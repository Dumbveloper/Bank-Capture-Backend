package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.mvc.domain.MainTask;
import web.mvc.domain.Reservation;
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
     * @return List<Reservation>
     */
    //List<Reservation> findReservationByCustomerId(Long customerId);
// b.bank_name AS bankName, b.bank_addr AS bankAddr,    INNER JOIN banker b ON r.bank_id = b.bank_id
    @Query(value = "SELECT NEW web.mvc.dto.mypage.CustomerScheduleResponseDTO(r.reservationId, b.bankName, b.bankAddr, r.reservationDate, r.reservationTime,t.taskName, r.comment, r.reservationFinishFlag) FROM Reservation r JOIN r.bank b JOIN r.task t LEFT JOIN r.customer c WHERE c.customerId = :customerId")
    List<CustomerScheduleResponseDTO> findReservationDetailsByCustomerId(@Param("customerId") Long customerId);
}
