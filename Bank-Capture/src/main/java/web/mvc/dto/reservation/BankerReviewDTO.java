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
@ToString
/**
 * /reservation/bankerinfo/
 * 예약하기 - 행원선택(행원정보)
 * BankerInfoResponseDTO 의 List<BankerReviewDTO> 에 들어가는 DTO
 */
public class BankerReviewDTO {
    private Long reservationId;  // primary key
    private String reservationDate;  //예약 날짜
    private int bankerStarRating;  // 행원리뷰 별점
    private String comment;  // 행원리뷰 코멘트

}
