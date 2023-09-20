package web.mvc.service.mypage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.mvc.domain.Reservation;

import web.mvc.dto.mypage.CustomerReviewRequestDTO;
import web.mvc.dto.mypage.CustomerScheduleResponseDTO;
import web.mvc.repository.ReservationRepository;


import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageCustomerServiceImpl implements MyPageCustomerService{

    private final ReservationRepository reservationRepository;

    @Override
    public List<CustomerScheduleResponseDTO> customerSchedule(Long customerId) {
        List<CustomerScheduleResponseDTO> list = reservationRepository.findReservationDetailsByCustomerId(customerId);

        return list;
    }


    @Override
    public String insertReview(CustomerReviewRequestDTO customerReviewRequestDTO) {
        Reservation reviewReservation = reservationRepository.findById(customerReviewRequestDTO.getReservationId()).orElse(null);
        reviewReservation.setComment(customerReviewRequestDTO.getBankerReviewComment());
        reviewReservation.setBankerStarRating(customerReviewRequestDTO.getBankerStarRating());
        reviewReservation.setBankStarRating(customerReviewRequestDTO.getBankStarRating());
        Reservation result = reservationRepository.save(reviewReservation);
        if(result ==null)
            return "fail";

        return "success";
    }

    @Override
    public String deleteReview(Long reservationId) {
        Reservation reviewReservation = reservationRepository.findById(reservationId).orElse(null);
        reviewReservation.setComment(null);
        reviewReservation.setBankStarRating(0);
        reviewReservation.setBankerStarRating(0);
        Reservation result = reservationRepository.save(reviewReservation);
        if(result ==null)
            return "fail";

        return "success";
    }
}