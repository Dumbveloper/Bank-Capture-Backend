package web.mvc.dto.mypage;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
/**
 * /myPage/banker/schedule/after
 * 마이페이지(행원) - 예약관리-방문후 예약
 */
public class BankerScheduleAfterResponseDTO {
    private String customerName;  // 고객 이름
    private String reservationDate;  // 예약 날짜
    private Long reservationId;  // 예약 iD
    private String reservationTime;  // 예약 시간
    private String taskName;  //업무 이름
    private String customerPhone;  //고객 전화번호
    private String bankerReviewComment;  // 행원리뷰 코멘트
}
