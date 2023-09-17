package web.mvc;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import web.mvc.domain.Customer;
import web.mvc.domain.Reservation;
import web.mvc.dto.mypage.CustomerReviewRequestDTO;
import web.mvc.repository.ReservationRepository;
import web.mvc.service.mypage.MyPageCustomerService;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MyPageCustomerTest {
    @Autowired
    private MyPageCustomerService myPageCustomerService;
    @Autowired
    private ReservationRepository reservationRepository;

    private Reservation dtoToEntity(CustomerReviewRequestDTO customerReviewRequestDTO) {
        Reservation reviewReservation = reservationRepository.findById(customerReviewRequestDTO.getReservationId()).orElse(null);
        reviewReservation.setComment(customerReviewRequestDTO.getBankerReviewComment());
        reviewReservation.setBankerStarRating(customerReviewRequestDTO.getBankerStarRating());
        reviewReservation.setBankStarRating(customerReviewRequestDTO.getBankStarRating());
        return reviewReservation;
    }

    @Test
    void 고객_리뷰남기기(){
        //given
        Reservation reservation = new Reservation();

        //when
        Long savedId = reservationRepository.save(reservation).getReservationId();
        //then
        assertEquals(reservation, reservationRepository.findById(savedId).get());
    }

    @Test
    public void 고객_리뷰삭제() throws Exception{
        //given
        Reservation reservation = new Reservation();
        Long savedId = reservationRepository.save(reservation).getReservationId();
        //then
        String result = myPageCustomerService.deleteReview(savedId);

        assertEquals("success",result); // 예약하기
    }



}
