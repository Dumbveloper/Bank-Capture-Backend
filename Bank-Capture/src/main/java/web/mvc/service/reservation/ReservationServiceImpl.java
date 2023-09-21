package web.mvc.service.reservation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import web.mvc.domain.*;
import web.mvc.dto.reservation.*;
import web.mvc.dto.sms.MessageDTO;
import web.mvc.dto.sms.SmsRequestDTO;
import web.mvc.dto.sms.SmsResponseDTO;
import web.mvc.repository.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{
    @Value("${naver-cloud-sms.accessKey}")
    private String accessKey;

    @Value("${naver-cloud-sms.secretKey}")
    private String secretKey;

    @Value("${naver-cloud-sms.serviceId}")
    private String serviceId;

    @Value("${naver-cloud-sms.senderPhone}")
    private String phone;

    private final JPAQueryFactory jpaQueryFactory;
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final BankerRepository bankerRepository;
    private final TaskRepository taskRepository;
    private final ScheduleRepository scheduleRepository;
    private final BankRepository bankRepository;

    @Override
    public List<BankDTO> findBankAll() {
        QBank bank = QBank.bank;
        QBankAverageStar bankAverageStar = QBankAverageStar.bankAverageStar;

        List<BankDTO> list = jpaQueryFactory.select(Projections.constructor(
                BankDTO.class,bank.bankId,bank.bankName,bank.locationX,bank.locationY
                ,bank.bankPhone,bank.bankAddr,bankAverageStar.avgStar))
                .from(bank).leftJoin(bankAverageStar)
                .on(bank.bankId.eq(bankAverageStar.bankId))
                .fetch();

        return list;
    }

    @Override
    public List<BankDTO> findBankSearchByName(String name) {
        QBank bank = QBank.bank;
        QBankAverageStar bankAverageStar = QBankAverageStar.bankAverageStar;

        List<BankDTO> list = jpaQueryFactory.select(Projections.constructor(
                        BankDTO.class,bank.bankId,bank.bankName,bank.locationX,bank.locationY
                        ,bank.bankPhone,bank.bankAddr,bankAverageStar.avgStar))
                .from(bank).leftJoin(bankAverageStar)
                .on(bank.bankId.eq(bankAverageStar.bankId))
                .where(bank.bankName.contains(name))
                .fetch();
        return list;
    }

    @Override
    public List<BankerAllResponseDTO> findBankerAll(Long bankId, Long taskId) {
        QBanker banker = QBanker.banker;                        //banker Q클래스 생성
        QSchedule schedule = QSchedule.schedule;                //schedule Q클래스
        QMainTask mainTask = QMainTask.mainTask;                //mainTask Q클래스
        QTask task = QTask.task;                                //task Q클래스
        QBankerRating bankerRating = QBankerRating.bankerRating;    //뷰에 매핑 될 bankerRating Q클래스 생성

        /**
         * QueryDSL로 banker, mainTask, task, 별점/리뷰수 뷰 조인하고
         *  Projectrions 사용하여 BankerAllResponseDTO 생성자로 직접 받기
         */
        List<BankerAllResponseDTO> bankerList = jpaQueryFactory.select(Projections.constructor(
                        BankerAllResponseDTO.class,banker.bankerId,banker.bankerName
                        ,banker.bankerCareer,banker.bankerImgPath,banker.bankerInfo
                        ,bankerRating.avgStar,bankerRating.cntComment,banker.bankerReviewFlag))
                .from(banker)
                .join(mainTask).on(banker.bankerId.eq(mainTask.banker.bankerId))
                .join(task).on(task.taskId.eq(mainTask.task.taskId))
                .leftJoin(bankerRating).on(bankerRating.bankerId.eq(banker.bankerId))
                .where(banker.bank.bankId.eq(bankId).and(task.taskId.eq(taskId)))
                .orderBy(bankerRating.avgStar.desc(),bankerRating.cntComment.desc())
                .fetch();

        /**
         * 행원별 스케줄리스트, 주업무 조회하여 BankerAllResponseDTO에 추가
         */
        for(int i = 0; i<bankerList.size();i++){
            /**
             * 스케줄리스트 조회
             * bankerList의 bankerid에 해당하는 스케줄리스트 조회 Query
             * */
            List<ScheduleDTO> schDto = jpaQueryFactory.select(Projections.constructor(
                            ScheduleDTO.class,schedule.banker.bankerId,schedule.bank.bankId,schedule.scheduleDate
                            ,schedule.time1,schedule.time2,schedule.time3,schedule.time4,schedule.time5,
                            schedule.time6,schedule.time7))
                    .from(schedule)
                    .where(schedule.banker.bankerId.eq(bankerList.get(i).getBankerId()))
                    .fetch();

            /**
             * 주업무 조회
             * bankerList의 bankerid에 해당하는 주업무 조회 Query
             * banker,mainTask,task 조인
             */
            List<TaskDTO> taskdto = jpaQueryFactory.select(Projections.constructor(TaskDTO.class, task.taskName))
                    .from(task)
                    .join(mainTask).on(task.taskId.eq(mainTask.task.taskId))
                    .join(banker).on(banker.bankerId.eq(mainTask.banker.bankerId))
                    .where(banker.bankerId.eq(bankerList.get(i).getBankerId()))
                    .fetch();

            //스케줄 리스트를 BankerAllResponseDTO에 삽입
            bankerList.get(i).setScheduleList(schDto);
            //주업무 리스트를 BankerAllResponseDTO에 삽입
            bankerList.get(i).setTaskList(taskdto);
        }
        return bankerList;
    }
    @Override
    public BankerInfoResponseDTO findBankerInfo(Long bankerId) {
        QBanker banker = QBanker.banker;
        QCertification certification = QCertification.certification;
        QBankerCertification bankerCertification = QBankerCertification.bankerCertification;
        QReservation reservation = QReservation.reservation;

        /**
         * Parameter bankerId가 가진 자격증 리스트 조회
         * banker, banker_certification, certification 조인
         */
        List<Certification> result = jpaQueryFactory.select(certification)
                .from(banker)
                .join(bankerCertification).on(banker.bankerId.eq(bankerCertification.banker.bankerId))
                .join(certification).on(bankerCertification.certification.certificationId.eq(certification.certificationId))
                .where(banker.bankerId.eq(bankerId))
                .fetch();

        /**
         * 조회한 자격증 리스트 domain -> dto로 생성
         */
        List<CertificationDTO> certificationDTOS = result.stream().map(
                certification1 -> {
                    return new CertificationDTO(certification1.getCertificationId(),certification1.getCertificationName());
                }
        ).collect(Collectors.toList());

        /**
         * bankerId에 해당하는 리뷰리스트, 레뷰 별 별점, 예약 날짜, 예약ID 조회
         */

        List<BankerReviewDTO> reDto = jpaQueryFactory.select(Projections.constructor(
                        BankerReviewDTO.class,reservation.reservationId,reservation.reservationDate
                        ,reservation.bankerStarRating,reservation.comment))
                .from(reservation)
                .where(reservation.banker.bankerId.eq(bankerId).and(reservation.comment.isNotNull()))
                .fetch();


        //자격증리스트, 리뷰리스트 BankerInfoResponseDTO로 생성
        BankerInfoResponseDTO bankerInfo = new BankerInfoResponseDTO(certificationDTOS,reDto);

        return bankerInfo;
    }
    @Override
    @Transactional
    public String doReservation(ReservationDTO reservationDTO)throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {

        Customer customer = customerRepository.findById(reservationDTO.getCustomerId()).orElse(null);
        Banker banker = bankerRepository.findById(reservationDTO.getBankerId()).orElse(null);
        Task task = taskRepository.findById(reservationDTO.getTaskId()).orElse(null);
        Bank bank = bankRepository.findById(reservationDTO.getBankId()).orElse(null);


        Reservation reservation = Reservation.builder().
                customer(customer).
                banker(banker).
                task(task).
                bank(bank).
                reservationDate(reservationDTO.getReservationDate()).
                reservationTime(reservationDTO.getReservationTime()).
                reservationFinishFlag(reservationDTO.getReservationFinishFlag()).
                build();

        //예약하기
        reservationRepository.save(reservation);

        QSchedule qSchedule = QSchedule.schedule;

        //예약 시간번호 앞에 "time"을 붙임
        String time = "time" + reservationDTO.getReservationTime();

        //예약시간번호를 스케줄테이블의 칼럼이름과 매핑
        Map<String, Path<?>> columnMap = new HashMap<>();
        columnMap.put("time1", qSchedule.time1);
        columnMap.put("time2", qSchedule.time2);
        columnMap.put("time3", qSchedule.time3);
        columnMap.put("time4", qSchedule.time4);
        columnMap.put("time5", qSchedule.time5);
        columnMap.put("time6", qSchedule.time6);
        columnMap.put("time7", qSchedule.time7);

        Path<?> columnPath = columnMap.get(time);

        //해당 예약시간에 해당하는 행원의 스케줄을 0으로 변경
        long update = jpaQueryFactory
                .update(qSchedule)
                .set((Path<Long>)columnPath, 0L)
                .where(qSchedule.banker.eq(banker), qSchedule.scheduleDate.eq(reservationDTO.getReservationDate()))
                .execute();
        String resDate =reservation.getReservationDate();
        String reservationYear = resDate.substring(0,4);
        String reservationMonth = resDate.substring(4,6);
        String reservationDay = resDate.substring(6,8);

        String message = new StringBuilder()
                .append("KB BankCapture 예약완료 \n\n")
                .append(customer.getCustomerName())
                .append("님 \n KB BankCapture 지점 방문 예약이 완료되었습니다.\n\n")
                .append("  지점명 : ").append(bank.getBankName())
                .append("\n  예약날짜 : ").append(reservationYear).append("년 ")
                .append(reservationMonth).append("월 ").append(reservationDay).append("일")
                .append("\n  예약 시간 : ").append((Integer.parseInt(reservation.getReservationTime())+9)).append("시")
                .append("\n  예약 행원 : ").append(banker.getBankerName()).append(" 행원")
                .append("\n  예약 업무 : ").append(task.getTaskName())
                .append("\n\n해당 업무에 필요한 서류를 아래 링크에서 확인하신 후, 필요서류를 구비하여 방문하시는 것을 추천드립니다.\n")
                .append("https://obank.kbstar.com/quics?page=C020003#loading")
                .toString();
        MessageDTO messageDto = MessageDTO.builder().to(customer.getCustomerPhone()).subject("KB BankCapture 방문예약 완료").content(message).build();
        sendSms(messageDto);

        //업데이트가 성공이면 "success"리턴
        if(update == 1)
            return "success";

        return "fail";

    }

    @Override
    @Transactional
    public String cancelReservation(Long reservationId)throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {

        QSchedule qSchedule = QSchedule.schedule;
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
        Banker banker = bankerRepository.findById(reservation.getBanker().getBankerId()).orElse(null);
        Customer customer = customerRepository.findById(reservation.getCustomer().getCustomerId()).orElse(null);
        //예약 시간번호 앞에 "time"을 붙임
        String time = "time" + reservation.getReservationTime();

        //예약시간번호를 스케줄테이블의 칼럼이름과 매핑
        Map<String, Path<?>> columnMap = new HashMap<>();
        columnMap.put("time1", qSchedule.time1);
        columnMap.put("time2", qSchedule.time2);
        columnMap.put("time3", qSchedule.time3);
        columnMap.put("time4", qSchedule.time4);
        columnMap.put("time5", qSchedule.time5);
        columnMap.put("time6", qSchedule.time6);
        columnMap.put("time7", qSchedule.time7);

        Path<?> columnPath = columnMap.get(time);

        long update = jpaQueryFactory
                .update(qSchedule)
                .set((Path<Long>)columnPath, 1L)
                .where(qSchedule.banker.eq(banker), qSchedule.scheduleDate.eq(reservation.getReservationDate()))
                .execute();
        String resDate =reservation.getReservationDate();
        String reservationYear = resDate.substring(0,4);
        String reservationMonth = resDate.substring(4,6);
        String reservationDay = resDate.substring(6,8);

        String message = new StringBuilder()
                .append("KB BankCapture 예약취소 \n\n")
                .append(customer.getCustomerName())
                .append("님 \n KB BankCapture ")
                .append(reservationYear).append("년 ")
                .append(reservationMonth).append("월 ").append(reservationDay).append("일")
                .append((Integer.parseInt(reservation.getReservationTime())+9)).append("시 ")
                .append("방문 예약이 취소되었습니다.\n")
                .append("\n 새로운 방문예약을 원하실 경우, KB Bank Capture 홈페이지를 방문해주세요.\n")
                .append("http://localhost:8081")
                .toString();
        MessageDTO messageDto = MessageDTO.builder().to(customer.getCustomerPhone()).subject("KB Bank Capture 방문예약 취소").content(message).build();
        sendSms(messageDto);

        //업데이트가 성공이면 예약을 삭제하고 "success"리턴
        if(update == 1) {
            reservationRepository.deleteById(reservationId);
            return "success";
        }

        return "fail";
    }

    @Override
    @Transactional
    public String changeReservation(ReservationDTO reservationDTO, Long reservationId)throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {

          QSchedule qSchedule = QSchedule.schedule;
          Reservation reservation = reservationRepository.findById(reservationId).orElse(null);
          Task task = taskRepository.findById(reservationDTO.getTaskId()).orElse(null);
          Banker afterBanker = bankerRepository.findById(reservationDTO.getBankerId()).orElse(null);
          Banker beforeBanker = bankerRepository.findById(reservation.getBanker().getBankerId()).orElse(null);
          Bank bank = bankRepository.findById(reservation.getBank().getBankId()).orElse(null);
          Customer customer = customerRepository.findById(reservation.getCustomer().getCustomerId()).orElse(null);


        //기존예약 시간번호 앞에 "time"을 붙임
          String openTime = "time" + reservation.getReservationTime();
          String beforeDate = reservation.getReservationDate();

          reservation.setReservationDate(reservationDTO.getReservationDate());
          reservation.setReservationFinishFlag(reservationDTO.getReservationFinishFlag());
          reservation.setReservationTime(reservationDTO.getReservationTime());
          reservation.setTask(task);
          reservation.setBanker(afterBanker);
          reservation.setReservationFinishFlag("F");


        //신규예약 시간번호 앞에 "time"을 붙임
        String closeTime = "time" + reservationDTO.getReservationTime();

        //예약시간번호를 스케줄테이블의 칼럼이름과 매핑
        Map<String, Path<?>> columnMap = new HashMap<>();
        columnMap.put("time1", qSchedule.time1);
        columnMap.put("time2", qSchedule.time2);
        columnMap.put("time3", qSchedule.time3);
        columnMap.put("time4", qSchedule.time4);
        columnMap.put("time5", qSchedule.time5);
        columnMap.put("time6", qSchedule.time6);
        columnMap.put("time7", qSchedule.time7);

        Path<?> openColumnPath = columnMap.get(openTime);
        Path<?> closeColumnPath = columnMap.get(closeTime);

        Long updateOpen = jpaQueryFactory
                .update(qSchedule)
                .set((Path<Long>)openColumnPath, 1L)
                .where(qSchedule.banker.eq(beforeBanker), qSchedule.scheduleDate.eq(beforeDate))
                .execute();

        Long updateClose = jpaQueryFactory
                .update(qSchedule)
                .set((Path<Long>)closeColumnPath, 0L)
                .where(qSchedule.banker.eq(afterBanker), qSchedule.scheduleDate.eq(reservationDTO.getReservationDate()))
                .execute();

        String resDate =reservation.getReservationDate();
        String reservationYear = resDate.substring(0,4);
        String reservationMonth = resDate.substring(4,6);
        String reservationDay = resDate.substring(6,8);

        String message = new StringBuilder()
                .append("KB BankCapture 예약변경 \n\n")
                .append(customer.getCustomerName())
                .append("님 \n KB BankCapture 지점 방문 예약이 변경되었습니다.\n\n")
                .append("  지점명 : ").append(bank.getBankName())
                .append("\n  예약날짜 : ").append(reservationYear).append("년 ")
                .append(reservationMonth).append("월 ").append(reservationDay).append("일")
                .append("\n  예약 시간 : ").append((Integer.parseInt(reservation.getReservationTime())+9)).append("시")
                .append("\n  예약 행원 : ").append(afterBanker.getBankerName()).append(" 행원")
                .append("\n  예약 업무 : ").append(task.getTaskName())
                .append("\n\n해당 업무에 필요한 서류를 아래 링크에서 확인하신 후, 필요서류를 구비하여 방문하시는 것을 추천드립니다.\n")
                .append("https://obank.kbstar.com/quics?page=C020003#loading")
                .toString();

        MessageDTO messageDto = MessageDTO.builder().to(customer.getCustomerPhone()).subject("KB BankCapture 방문예약 변경").content(message).build();
        sendSms(messageDto);

        if(updateOpen == 1L && updateClose == 1L){
            return "success";
        }

        return "fail";

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
    public String makeSignature(Long time) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
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
