package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.domain.Certification;
import web.mvc.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    /**
     * 고객 조회 - 로그인, 회원가입시 해당 이메일을 가진 고객이 있는지 조회
     * @param customerEmail
     * @return
     */
    Customer findByCustomerEmail(String customerEmail);

    /**
     * 고객 조회 - 로그인시 해당 이메일, 비밀번호를 가진 고객이 있는지 조회
     * @param customerEmail
     * @param customerPassword
     * @return
     */
    Customer findByCustomerEmailAndCustomerPassword(String customerEmail, String customerPassword);
}
