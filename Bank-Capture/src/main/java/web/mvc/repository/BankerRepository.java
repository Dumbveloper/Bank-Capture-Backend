package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.Bank;
import web.mvc.domain.Banker;
import web.mvc.domain.Customer;

import java.util.List;

public interface BankerRepository extends JpaRepository<Banker,Long> {

    /**
     *
     * @param customerEmail
     * @param customerPassword
     * @return
     */
    Banker findByBanker(String customerEmail, String customerPassword);

    /**
     * /reservation/bankerAll
     * 예약하기 - 예약가능한 행원 조회 / 해당 지점 내의 행원 전부 찾기
     *
     * @param bankId
     * @return List<Banker>
     */
    List<Banker> findBanker(String bankId);
}
