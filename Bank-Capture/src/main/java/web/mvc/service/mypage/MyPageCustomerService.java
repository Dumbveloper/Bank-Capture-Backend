package web.mvc.service.mypage;

import web.mvc.dto.mypage.CustomerReviewRequestDTO;
import web.mvc.dto.mypage.CustomerScheduleResponseDTO;

import java.util.List;

public interface MyPageCustomerService {

    /**
     * /myPage/customer/schedule
     * 마이페이지(고객) - 예약관리
     * Reservation, Bank , Document , task
     *
     * @param customerId
     * @return List<CustomerScheduleResponseDTO>
     */
    public List<CustomerScheduleResponseDTO> customerSchedule(Long customerId);


    /**
     * /myPage/customer/review
     * 마이페이지(고객) - 리뷰작성
     * BankReview , BankerReview
     *
     * @param customerReviewRequestDTO
     * @return Object
     */
    public int insertReview(CustomerReviewRequestDTO customerReviewRequestDTO);

}
