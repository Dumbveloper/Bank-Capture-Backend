package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.domain.Bank;

import java.util.List;
import java.util.Map;

public interface BankRepository extends JpaRepository<Bank,Long> {

}
