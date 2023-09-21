package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.BankerCertification;
import web.mvc.domain.Certification;

import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification,Long> {
}
