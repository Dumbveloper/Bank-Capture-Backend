package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.MainTask;
import web.mvc.domain.Reservation;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    /**
     * /reservation/done
     * 예약하기
     *
     * @return String 성공여부
     */
    String insertReservation(Reservation reservation);

    /**
     * 예약취소
     *
     * @param reservationId
     * @return String 성공여부
     */
    String deleteReservation(String reservationId);

    /**
     * /myPage/customer/schedule
     * 마이페이지(고객) - 예약관리 / 해당 고객의 예약목록 조회
     *
     * @param customerId
     * @return List<Reservation>
     */
    List<Reservation> findReservationByCustomerId(Long customerId);


}
