package web.mvc.service.mypage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.mvc.domain.Banker;
import web.mvc.domain.Reservation;

import web.mvc.dto.mypage.CustomerReviewRequestDTO;
import web.mvc.dto.mypage.CustomerScheduleResponseDTO;
import web.mvc.repository.ReservationRepository;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service

public class MyPageCustomerServiceImpl implements MyPageCustomerService{
    @Autowired
    private ReservationRepository reservationRep;
    @Override
    public List<CustomerScheduleResponseDTO> customerSchedule(Long customerId) {
        List<CustomerScheduleResponseDTO> list =reservationRep.findReservationDetailsByCustomerId(customerId);


        return list;
    }


    @Override
    public int insertReview(CustomerReviewRequestDTO customerReviewRequestDTO) {
        Reservation reviewReservation = reservationRep.findById(customerReviewRequestDTO.getReservationId()).orElse(null);
        reviewReservation.setComment(customerReviewRequestDTO.getBankerReviewComment());
        reviewReservation.setBankerStarRating(customerReviewRequestDTO.getBankerStarRating());
        reviewReservation.setBankStarRating(customerReviewRequestDTO.getBankStarRating());
        Reservation result = reservationRep.save(reviewReservation);
        if(result ==null)
            return 0;

        return 1;
    }

    @Override
    public int deletetReview(Long reservationId) {
        Reservation reviewReservation = reservationRep.findById(reservationId).orElse(null);
        reviewReservation.setComment(null);
        reviewReservation.setBankStarRating(0);
        reviewReservation.setBankerStarRating(0);
        Reservation result = reservationRep.save(reviewReservation);
        if(result ==null)
            return 0;

        return 1;
    }
}