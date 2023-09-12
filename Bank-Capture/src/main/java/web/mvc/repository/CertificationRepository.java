package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.BankerCertification;
import web.mvc.domain.Certification;

import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification,Long> {
    /**
     * /reservation/bankerinfo/
     * 예약하기 - 행원선택(행원정보) / 행원 자격증 검색
     *
     * @param bankerId
     * @return List<Certification>
     */
    List<Certification> findCertificationByBankerId(String bankerId);

}
