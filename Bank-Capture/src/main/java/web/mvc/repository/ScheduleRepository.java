package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.Reservation;
import web.mvc.domain.Schedule;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

}
