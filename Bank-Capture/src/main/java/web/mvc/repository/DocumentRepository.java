package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.Customer;
import web.mvc.domain.Document;

public interface DocumentRepository extends JpaRepository<Document,Long> {
}
