package web.mvc.service.mypage;

import web.mvc.dto.mypage.CustomerReviewRequestDTO;
import web.mvc.dto.mypage.CustomerScheduleAfterResponseDTO;
import web.mvc.dto.mypage.CustomerScheduleBeforeResponseDTO;

import java.util.List;

public interface MyPageCustomerService {

    /**
     * /myPage/customer/schedule/before
     * 마이페이지(고객) - 예약관리-방문전
     * Reservation, Bank , Document , task
     *
     * @param customerId
     * @param finishFlag
     * @return List<CustomerScheduleBeforeResponseDTO>
     */
    public List<CustomerScheduleBeforeResponseDTO> customerScheduleBefore(Long customerId, String finishFlag);

    /**
     * /myPage/customer/schedule/after
     * 마이페이지(고객) - 예약관리-방문후
     * Reservation, Bank , task , BankReview
     *
     * @param customerId
     * @param finishFlag
     * @return List<CustomerScheduleAfterResponseDTO>
     */
    public List<CustomerScheduleAfterResponseDTO> customerScheduleAfter(Long customerId, String finishFlag);

    /**
     * /myPage/customer/review
     * 마이페이지(고객) - 리뷰작성
     * BankReview , BankerReview
     *
     * @param customerReviewRequestDTO
     * @return Object
     */
    public Object insertReview(CustomerReviewRequestDTO customerReviewRequestDTO);
}
