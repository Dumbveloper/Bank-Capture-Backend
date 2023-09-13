package web.mvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import web.mvc.domain.*;
import web.mvc.dto.mypage.BankerRankingResponseDTO;
import web.mvc.dto.mypage.BankerScheduleResponseDTO;
import web.mvc.dto.mypage.CustomerReviewRequestDTO;
import web.mvc.dto.mypage.CustomerScheduleResponseDTO;
import web.mvc.dto.reservation.BankDTO;
import web.mvc.dto.reservation.ReservationDTO;
import web.mvc.dto.reservation.ScheduleDTO;
import web.mvc.dto.users.BankerLoginRequestDTO;
import web.mvc.dto.users.CustomerDTO;
import web.mvc.dto.users.CustomerLoginRequestDTO;
import web.mvc.repository.*;
import web.mvc.service.mypage.MyPageBankerService;
import web.mvc.service.mypage.MyPageCustomerService;
import web.mvc.service.mypage.MyPageCustomerServiceImpl;
import web.mvc.service.reservation.ReservationService;
import web.mvc.service.user.UserService;

import javax.transaction.Transactional;
import java.util.List;
//import web.mvc.banker.repository.BankerRepository;
//import web.mvc.domain.*;
//import web.mvc.domain.BankerReview;
//import web.mvc.dto.BankerReviewDTO;
//import web.mvc.repository.BankerRepository;
//import web.mvc.repository.DocumentRepository;
//import web.mvc.repository.ReservationRepository;
//import web.mvc.reservation.service.ReservationService;
//import web.mvc.review.repository.BankReviewRepository;
//import web.mvc.review.repository.BankerReviewRepository;
//import web.mvc.review.service.ReviewServiceImpl;


@SpringBootTest
class BankCaptureApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BankerRepository bankerRepository;

    @Autowired
    private MyPageCustomerService myPageCustomerService;
    @Autowired
    private MyPageBankerService myPageBankerService;

    CustomerLoginRequestDTO customerLoginRequestDTO = new CustomerLoginRequestDTO("wonsik1@naver.com", "1234");

    BankerLoginRequestDTO bankerLoginRequestDTO = new BankerLoginRequestDTO("test@naver.com", "1234");

    CustomerDTO customerDTO = new CustomerDTO("원식", "wonsik1@naver.com", "1234", "010-0000-1111");

    ReservationDTO reservationDTO = new ReservationDTO(1L, 1L, 1L, 1L,"20230304", "4", "F");

    @Test
    @Rollback(value = false)
    void 회원가입() {
        String result = userService.register(customerDTO);
        System.out.println(result);
    }

    @Test
    void 회원로그인() {
        String result  = userService.customerLogin(customerLoginRequestDTO);
        System.out.println(result);
    }

    @Test
    void 행원로그인() {
        String result = userService.bankerLogin(bankerLoginRequestDTO);
        System.out.println(result);
    }


    @Test
    @Rollback(value = false)
    void 예약하기() {
        String result = reservationService.doReservation(reservationDTO, 1L);
        System.out.println(result);
    }
    @Test
    @Rollback(value = false)
    void 예약변경() {
        String result = reservationService.changeReservation(reservationDTO, 4L);
        System.out.println(result);
    }

    @Test
    @Rollback(value = false)
    void 예약취소() {
        String result = reservationService.cancelReservation(4L);
        System.out.println(result);
    }
//    void insertReviews() {
//
//
//        Banker banker1= Banker.builder().bankerName("신동렬").build();
//        Banker banker2= Banker.builder().bankerName("유유정").build();
//        Banker banker3= Banker.builder().bankerName("나웅기").build();
//        Banker banker4= Banker.builder().bankerName("오원식").build();
//
//        bankerRepository.save(banker1);
//        bankerRepository.save(banker2);
//        bankerRepository.save(banker3);
//        bankerRepository.save(banker4);
//
//        reviewService.insertBankerReview(BankerReview.builder().comment("친절해요").starRating(3).banker(banker1).build());
//        reviewService.insertBankerReview(BankerReview.builder().comment("친절ㅋ").starRating(3).banker(banker2).build());
//        reviewService.insertBankerReview(BankerReview.builder().comment("별로 ㅋㅋ").starRating(2).banker(banker1).build());
//        reviewService.insertBankerReview(BankerReview.builder().comment("그닥").starRating(5).banker(banker2).build());
//        reviewService.insertBankerReview(BankerReview.builder().comment("불친절").starRating(4).banker(banker4).build());
//        reviewService.insertBankerReview(BankerReview.builder().comment("흠..").starRating(2).banker(banker3).build());
//
//
//    }
//    @Test
//    void findReviewById(){
//        List<BankerReview> bankerReviewById = reviewService.findByBanker(1L);
//        bankerReviewById.forEach(b->System.out.println("bankerReviewById = "+b));
//    }

    @Test
    void contextLoads() {
//        Customer customer1 = Customer.builder().customerName("동렬").build();
//        customerRepository.save(customer1);
        Banker banker1 = Banker.builder().bankerName("웅기").bankerCareer("2년").bankerInfo("웃자웃자").bankerEmail(bankerLoginRequestDTO.getEmail()).build();
        bankerRepository.save(banker1);

        Banker banker2 = Banker.builder().bankerName("봉섭").bankerCareer("4년").bankerInfo("어라라").bankerEmail("kk@naver.com").build();
        bankerRepository.save(banker2);
        Bank bank1 = Bank.builder().bankAddr("서울시 양재대로").bankName("선릉점").bankPhone("02-111-1111").build();
        bankRepository.save(bank1);
        Task task1 = Task.builder().taskName("개인대출").build();
        taskRepository.save(task1);
        Reservation res = Reservation.builder().
                reservationDate("20230304").
                reservationTime("4")
                .customer(Customer.builder().customerId(1L).build())
                .banker(Banker.builder().bankerId(1L).build())
                .task(Task.builder().taskId(1L).build())
                .bank(Bank.builder().bankId(1L).build()).build();
        reservationRepository.save(res);
//        Customer customer1 = Customer.builder().customerName("유정").build();
//        customerRepository.save(customer1);
//        Banker banker1 = Banker.builder().bankerName("지현").bankerCareer("3년").bankerInfo("잘부탁드려요").build();
//        bankerRepository.save(banker1);
//        Bank bank1 = Bank.builder().bankAddr("서울시 양재대로").bankName("선릉점").bankPhone("02-111-1111").build();
//        bankRepository.save(bank1);
//        Task task1 = Task.builder().taskName("적금").build();
//       taskRepository.save(task1);
//
//        Reservation res = Reservation.builder().
//                reservationDate("20220304").
//                reservationTime("2")
//                .customer(customer1)
//                .banker(banker1)
//                .task(task1)
//                .bank(Bank.builder().bankId(3L).build()).build();
//        reservationRepository.save(res);
    }

    @Test
    void printBankDTO(){
        //현재는 test하면 에러가 나는데
        //모든 bank_id의 평균별점 데이터를 채워주지 않아서 error 발생했음
        List<BankDTO> list=reservationService.findBankAll();
        for(BankDTO dto:list){
            System.out.println(dto);
        }}

        /**
         * insertReview
         */
    @Test
    void insertReview(){
        int review = myPageCustomerService.insertReview(CustomerReviewRequestDTO.builder().reservationId(3L).bankerReviewComment("완친")
                .bankStarRating(5)
                .bankerStarRating(5).build());
        System.out.println(review);
    }
    @Test
    void deleteReview(){
        int review = myPageCustomerService.deleteReview(3L);
        System.out.println(review);
    }

    @Test
    void findReservationByCustomerId(){
        List<CustomerScheduleResponseDTO> list = myPageCustomerService.customerSchedule(1L);
        list.forEach(b -> System.out.println(b));
    }
    @Test
    void findReservationByBankerId(){
        List<BankerScheduleResponseDTO> list = myPageBankerService.bankerSchedule(1L);
        list.forEach(b -> System.out.println(b));
    }

    @Test
    void insertSchedule(){
        ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                .bankerId(1L)
                .bankId(1L)
                .scheduleDate("20230304")
                .time1(1)
                .time2(1)
                .time3(1)
                .time4(1)
                .time5(1)
                .time6(1)
                .time7(1)
                .build();
        Schedule schedule = myPageBankerService.checkTime(scheduleDTO);
        System.out.println(schedule);
    }


    @Test
    void updateFlag(){
        int review = myPageBankerService.updateFlag(3L);
        System.out.println(review);
    }

    @Test
    void bankerRanking(){

        List<BankerRankingResponseDTO> list = myPageBankerService.bankerRanking(1L,0,3).toList();
        System.out.println(list);
    }


}
