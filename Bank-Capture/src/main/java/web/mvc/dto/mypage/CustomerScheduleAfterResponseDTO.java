package web.mvc.dto.mypage;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
/**
 * /myPage/customer/schedule/after
 * 마이페이지(고객) - 예약관리-방문후
 */
public class CustomerScheduleAfterResponseDTO {
    private String bankName; // 지점이름
    private String bankAddr; // 지점주소
    private String reservationDate;  // 예약 날짜
    private String reservationTime;  // 예약 시간
    private String taskName;  //업무 이름
    private Long reservationId;  // 예약 iD
    private String bankerReviewComment;  // 행원리뷰 코멘트
}
