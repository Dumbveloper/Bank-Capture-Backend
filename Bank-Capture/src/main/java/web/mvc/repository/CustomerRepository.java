package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.domain.Certification;
import web.mvc.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    /**
     *
     * @param customerEmail
     * @param customerPassword
     * @return
     */
    Customer findBy(String customerEmail, String customerPassword);
}
