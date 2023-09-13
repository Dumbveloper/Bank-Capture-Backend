package web.mvc;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import web.mvc.domain.Certification;
import web.mvc.domain.QBanker;
import web.mvc.domain.QBankerCertification;
import web.mvc.domain.QCertification;
import web.mvc.domain.review.BankerReview;
import web.mvc.dto.reservation.BankerInfoResponseDTO;
import web.mvc.dto.reservation.BankerReviewDTO;
import web.mvc.dto.reservation.CertificationDTO;
import web.mvc.repository.review.BankerReviewRepository;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class FindBankerInfo {

    @Autowired
    private JPAQueryFactory queryFactory;
    @Autowired
    BankerReviewRepository bankerReviewRep;

    @Test
    public void 행원자격증리뷰리스트조회(){
        QBanker banker = QBanker.banker;
        QCertification certification = QCertification.certification;
        QBankerCertification bankerCertification = QBankerCertification.bankerCertification;

        List<Certification> result = queryFactory.select(certification)
                .from(banker)
                .join(bankerCertification).on(banker.bankerId.eq(bankerCertification.banker.bankerId))
                .join(certification).on(bankerCertification.certification.certificationId.eq(certification.certificationId))
                .where(banker.bankerId.eq(5L))
                .fetch();

        List<CertificationDTO> certificationDTOS = result.stream().map(
                certification1 -> {
                    return new CertificationDTO(certification1.getCertificationId(),certification1.getCertificationName());
                }
        ).collect(Collectors.toList());

        List<BankerReview> list = bankerReviewRep.findBankerReviewByBanker_bankerId(5L);

        List<BankerReviewDTO> bankerReviewDTOS = list.stream().map(
                bankerReview -> {
                    return new BankerReviewDTO(bankerReview.getBankerReviewId(),bankerReview.getReservation()
                            ,bankerReview.getBanker(),bankerReview.getCustomer(),bankerReview.getBankerReviewDate()
                            ,bankerReview.getBankerStarRating(),bankerReview.getBankerReviewComment());
                }
        ).collect(Collectors.toList());

        BankerInfoResponseDTO bankerInfo = new BankerInfoResponseDTO(certificationDTOS,bankerReviewDTOS);


        System.out.println("***********************"+bankerInfo);
    }



}
