package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.BankerCertification;

public interface BankerCertificationRepository extends JpaRepository<BankerCertification,Long> {
}
