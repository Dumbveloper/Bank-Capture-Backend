package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.domain.Bank;

import java.util.List;
import java.util.Map;

public interface BankRepository extends JpaRepository<Bank,Long> {
    @Query(nativeQuery = true, value = "SELECT DISTINCT b.*, bas.avg_star FROM testing.bank b " +
            "left join testing.bankaveragestar bas on b.bank_id=bas.bank_id")
    List<Map<String, Object>> findDistinctAvgStar();

}
