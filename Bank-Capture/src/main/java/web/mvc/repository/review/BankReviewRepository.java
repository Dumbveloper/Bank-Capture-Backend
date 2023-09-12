package web.mvc.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;
import web.mvc.domain.review.BankReview;

public interface BankReviewRepository extends JpaRepository<BankReview,Long> {
}
