package web.mvc.dto.mypage;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
/**
 * /myPage/customer/review
 * 마이페이지(고객) - 리뷰작성
 */
public class CustomerReviewRequestDTO {
    private Long reservationId; // 예약 iD
    private int bankStarRating;  // 지점 리뷰 별점
    private int bankerStarRating;  // 행원리뷰 별점
    private String bankerReviewComment;  // 행원리뷰 코멘트
}
