package web.mvc.dto.reservation;

import lombok.*;
import web.mvc.domain.Banker;
import web.mvc.domain.Customer;
import web.mvc.domain.Reservation;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
/**
 * /reservation/bankerinfo/
 * 예약하기 - 행원선택(행원정보)
 * BankerInfoResponseDTO 의 List<BankerReviewDTO> 에 들어가는 DTO
 */
public class BankerReviewDTO {
    private Long bankerReviewId;  // primary key
    private Reservation reservation;  //예약 ID
    private Banker banker;  //행원
    private Customer customer;  //고객
    private String bankerReviewDate;  //리뷰 작성 날짜
    private int bankerStarRating;  // 행원리뷰 별점
    private String bankerReviewComment;  // 행원리뷰 코멘트

}
