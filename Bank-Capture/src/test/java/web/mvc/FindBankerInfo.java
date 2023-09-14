package web.mvc;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import web.mvc.domain.*;
import web.mvc.dto.reservation.BankerInfoResponseDTO;
import web.mvc.dto.reservation.BankerReviewDTO;
import web.mvc.dto.reservation.CertificationDTO;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class FindBankerInfo {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Test
    public void 행원자격증리뷰리스트조회(){
        QBanker banker = QBanker.banker;
        QCertification certification = QCertification.certification;
        QBankerCertification bankerCertification = QBankerCertification.bankerCertification;
        QReservation reservation = QReservation.reservation;

        /**
         * Parameter bankerId가 가진 자격증 리스트 조회
         * banker, banker_certification, certification 조인
         */
        List<Certification> result = jpaQueryFactory.select(certification)
                .from(banker)
                .join(bankerCertification).on(banker.bankerId.eq(bankerCertification.banker.bankerId))
                .join(certification).on(bankerCertification.certification.certificationId.eq(certification.certificationId))
                .where(banker.bankerId.eq(1L))
                .fetch();

        /**
         * 조회한 자격증 리스트 domain -> dto로 생성
         */
        List<CertificationDTO> certificationDTOS = result.stream().map(
                certification1 -> {
                    return new CertificationDTO(certification1.getCertificationId(),certification1.getCertificationName());
                }
        ).collect(Collectors.toList());

        /**
         * bankerId에 해당하는 리뷰 리스트 조회
         */

        List<BankerReviewDTO> reDto = jpaQueryFactory.select(Projections.constructor(
                        BankerReviewDTO.class,reservation.comment))
                .from(reservation)
                .where(reservation.banker.bankerId.eq(1L))
                .fetch();


        //자격증리스트, 리뷰리스트 BankerInfoResponseDTO로 생성
        BankerInfoResponseDTO bankerInfo = new BankerInfoResponseDTO(certificationDTOS,reDto);

        System.out.println(bankerInfo);
    }



}
