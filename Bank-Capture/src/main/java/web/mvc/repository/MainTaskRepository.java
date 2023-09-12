package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.Document;
import web.mvc.domain.MainTask;

public interface MainTaskRepository extends JpaRepository<MainTask,Long> {
}
