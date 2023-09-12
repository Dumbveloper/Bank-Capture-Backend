package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.Reservation;
import web.mvc.domain.Schedule;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    /**
     * /reservation/bankerAll
     * 예약하기 - 예약가능한 행원 조회 / 해당 행원의 스케줄 검색
     *
     * @param bankerId
     * @return
     */
    List<Schedule> findScheduleByBankerId(String bankerId);

    /**
     * /reservation/done
     * 예약하기 / 고객의 예약으로 인한 스케줄 변경
     *
     * @param reservation
     * @return String
     */
    String updateScheduleByReservation(Reservation reservation);

    /**
     * 예약취소 / 예약취소로 인한 스케줄 변경
     * @param reservationId
     * @return
     */
    String updateScheduleByCancel(String reservationId);
}
