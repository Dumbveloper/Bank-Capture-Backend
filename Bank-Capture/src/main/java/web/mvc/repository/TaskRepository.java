package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.Schedule;
import web.mvc.domain.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

}
