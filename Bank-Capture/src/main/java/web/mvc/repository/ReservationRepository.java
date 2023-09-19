package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.mvc.domain.MainTask;
import web.mvc.domain.Reservation;
import web.mvc.dto.mypage.BankerScheduleResponseDTO;
import web.mvc.dto.mypage.CustomerScheduleResponseDTO;

import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    /**
     * /reservation/done
     * 예약하기
     *
     * @return String 성공여부
     */
    //String insertReservation(Reservation reservation);

    /**
     * /reservation/bankerinfo/
     * 예약하기 - 행원선택(행원정보) / 행원의 리뷰 조회
     *
     * @param bankerId
     * @return List<BankerReview>
     */

    //행원에 해당하는 리뷰리스트 검색
    //List<BankerReview> findBankerReviewByBanker_bankerId(Long bankerId);



    /**
     * 예약취소
     *
     * @param reservationId
     * @return String 성공여부
     */
    //String deleteReservation(String reservationId);

    /**
     * /myPage/customer/schedule
     * 마이페이지(고객) - 예약관리 / 해당 고객의 예약목록 조회
     *
     * @param customerId
     * @return List<CustomerScheduleResponseDTO>
     */
    //List<Reservation> findReservationByCustomerId(Long customerId);
// b.bank_name AS bankName, b.bank_addr AS bankAddr,    INNER JOIN banker b ON r.bank_id = b.bank_id
    @Query(value = "SELECT NEW web.mvc.dto.mypage.CustomerScheduleResponseDTO(r.reservationId, b.bankId, b.bankName, b.bankAddr, r.reservationDate, r.reservationTime, bk.bankerName,t.taskName, r.comment, r.reservationFinishFlag) FROM Reservation r JOIN r.bank b JOIN r.task t JOIN r.banker bk LEFT JOIN r.customer c WHERE c.customerId = :customerId")
    List<CustomerScheduleResponseDTO> findReservationDetailsByCustomerId(@Param("customerId") Long customerId);

    /**
     * /myPage/banker/schedule
     * 마이페이지(고객) - 예약관리 / 해당 고객의 예약목록 조회
     *
     * @param bankerId
     * @return List<BankerScheduleResponseDTO>
     */
    @Query(value = "SELECT NEW web.mvc.dto.mypage.BankerScheduleResponseDTO(r.reservationId, c.customerName, c.customerPhone, r.reservationDate, r.reservationTime,t.taskName, r.comment, r.reservationFinishFlag) FROM Reservation r JOIN r.customer c JOIN r.task t LEFT JOIN r.banker b WHERE b.bankerId = :bankerId")
    List<BankerScheduleResponseDTO> findReservationDetailsByBankerId(Long bankerId);
}
