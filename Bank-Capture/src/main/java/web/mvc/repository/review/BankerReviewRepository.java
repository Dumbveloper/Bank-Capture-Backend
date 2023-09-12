package web.mvc.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.review.BankerReview;

import java.util.List;

public interface BankerReviewRepository extends JpaRepository<BankerReview,Long> {
    /**
     * /reservation/bankerinfo/
     * 예약하기 - 행원선택(행원정보) / 행원의 리뷰 조회
     *
     * @param bankerId
     * @return List<BankerReview>
     */
    List<BankerReview> findBankerReviewByBankerId(String bankerId);
}
