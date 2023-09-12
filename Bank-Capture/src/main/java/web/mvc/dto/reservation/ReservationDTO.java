package web.mvc.dto.reservation;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
/**
 * /reservation/done
 * 예약하기
 */
public class ReservationDTO {
    private Long customerId;  //고객 ID
    private Long bankerId;  //행원 ID
    private Long taskId;  //업무 ID
    private String reservationDate;  // 예약 날짜
    private String reservationTime;  // 예약 시간
    private String reservationFinishFlag;  // 예약 완료 여부

}
