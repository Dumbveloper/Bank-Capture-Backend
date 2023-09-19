package web.mvc.service.mypage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import web.mvc.domain.*;
import web.mvc.dto.mypage.BankerRankingResponseDTO;
import web.mvc.dto.mypage.BankerScheduleResponseDTO;
import web.mvc.dto.reservation.ScheduleDTO;
import web.mvc.dto.sms.MessageDTO;
import web.mvc.dto.sms.SmsRequestDTO;
import web.mvc.dto.sms.SmsResponseDTO;
import web.mvc.repository.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MyPageBankerServiceImpl implements MyPageBankerService{
    @Value("${naver-cloud-sms.accessKey}")
    private String accessKey;

    @Value("${naver-cloud-sms.secretKey}")
    private String secretKey;

    @Value("${naver-cloud-sms.serviceId}")
    private String serviceId;

    @Value("${naver-cloud-sms.senderPhone}")
    private String phone;

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private BankerRepository bankerRepository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CustomerRepository customerRep;
    @Override
    public List<BankerScheduleResponseDTO> bankerSchedule(Long bankerId) {
        List<BankerScheduleResponseDTO> list = reservationRepository.findReservationDetailsByBankerId(bankerId);
        return list;
    }



    @Override
    public Page<BankerRankingResponseDTO> bankerRanking(Long bankId,int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return bankerRepository.findBankerRankingByBankId(bankId, pageable);
    }

    @Override
    public String checkTime(ScheduleDTO scheduleDTO) {
        Banker insertBanker = bankerRepository.findById(scheduleDTO.getBankerId()).orElse(null);
        Bank insertBank = bankRepository.findById(scheduleDTO.getBankId()).orElse(null);
        Schedule insertSchedule = Schedule.builder()
                .banker(insertBanker)
                .bank(insertBank)
                .scheduleDate(scheduleDTO.getScheduleDate())
                .time1(scheduleDTO.getTime1())
                .time2(scheduleDTO.getTime2())
                .time3(scheduleDTO.getTime3())
                .time4(scheduleDTO.getTime4())
                .time5(scheduleDTO.getTime5())
                .time6(scheduleDTO.getTime6())
                .time7(scheduleDTO.getTime7()).build();
        scheduleRepository.save(insertSchedule);


        return "success";
    }

    @Override
    public String updateFlag(Long reservationId) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        Reservation reviewReservation = reservationRepository.findById(reservationId).orElse(null);
        Customer customer = customerRep.findById(reviewReservation.getCustomer().getCustomerId()).orElse(null);

        reviewReservation.setReservationFinishFlag("T");
        reservationRepository.save(reviewReservation);

        String message = new StringBuilder()
                .append("KB BankCapture 방문 완료 \n\n")
                .append(customer.getCustomerName())
                .append("님 KB BankCapture 방문은 만족스러우셨나요? \n\n")
                .append("더 나은 서비스 제공과 다른 이용자들을 위해서 리뷰를 남겨주세요.\n")
                .append("http://localhost:8081")
                .toString();
        MessageDTO messageDto = MessageDTO.builder().to(customer.getCustomerPhone()).subject("KB BankCapture 은행 방문 완료").content(message).build();
        sendSms(messageDto);

        return "success";
    }
    public SmsResponseDTO sendSms(MessageDTO messageDto) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Long systime = System.currentTimeMillis();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", systime.toString());
        headers.set("x-ncp-iam-access-key", accessKey);
        headers.set("x-ncp-apigw-signature-v2", makeSignature(systime));

        List<MessageDTO> messages = new ArrayList<>();
        messages.add(messageDto);
        System.out.println(messages);

        SmsRequestDTO request = SmsRequestDTO.builder()
                .type("LMS")
                .contentType("COMM")
                .countryCode("82")
                .from(phone)
                .content("안녕하세요.")
                .messages(messages)
                .build();
        System.out.println(request);
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(request);
        HttpEntity<String> httpBody = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        SmsResponseDTO response = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+ serviceId +"/messages"), httpBody, SmsResponseDTO.class);

        return response;
    }

    //secretKey 암호화
    public String makeSignature(Long time) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidKeyException {
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/"+ this.serviceId+"/messages";
        String timestamp = time.toString();
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;
    }
}
