package web.mvc.dto.mypage;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
/**
 * /myPage/banker/schedule/before
 * 마이페이지(행원) - 예약관리-오늘의 방문
 */
public class BankerScheduleBeforeResponseDTO {

    private String customerName;  // 고객 이름
    private String reservationDate;  // 예약 날짜
    private String reservationTime;  // 예약 시간
    private String taskName;  //업무 이름
    private String customerPhone;  //고객 전화번호

}
