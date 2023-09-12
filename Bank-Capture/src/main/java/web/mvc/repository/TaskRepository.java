package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.Schedule;
import web.mvc.domain.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    /**
     * /reservation/bankerAll
     * 예약하기 - 예약가능한 행원 조회 / 행원의 업무 리스트 조회
     *
     * @param bankerId
     * @return List<Task>
     */
    List<Task> findTaskByBankerId(String bankerId);
}
