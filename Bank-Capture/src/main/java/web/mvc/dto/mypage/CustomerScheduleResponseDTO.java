package web.mvc.dto.mypage;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
/**
 * /myPage/customer/schedule
 * 마이페이지(고객) - 예약관리-방문후
 */
public class CustomerScheduleResponseDTO {
    private Long reservationId;  // 예약 iD
    private String bankName; // 지점이름
    private String bankAddr; // 지점주소
    private String reservationDate;  // 예약 날짜
    private String reservationTime;  // 예약 시간
    private String taskName;  //업무 이름
  //  private String documentLink;  // 필요서류 링크
    private String bankerReviewComment;  // 행원리뷰 코멘트
    private String finishFlag; // 고객 방문 여부 플래그
}
