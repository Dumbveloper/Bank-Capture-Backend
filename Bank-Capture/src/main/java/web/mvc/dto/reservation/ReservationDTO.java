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
    private Long reservationId;
    private Long customerId;  //고객 ID
    private Long bankerId;  //행원 ID
    private Long taskId;  //업무 ID
    private Long bankId;    //지점 ID
    private String reservationDate;  // 예약 날짜
    private String reservationTime;  // 예약 시간
    private String reservationFinishFlag;  // 예약 완료 여부
    public ReservationDTO(Long customerId, Long bankerId, Long taskId, Long bankId, String reservationDate, String reservationTime, String reservationFinishFlag) {
        this.customerId = customerId;
        this.bankerId = bankerId;
        this.taskId = taskId;
        this.bankId = bankId;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.reservationFinishFlag = reservationFinishFlag;
    }

    public ReservationDTO(Long customerId, Long bankerId, Long taskId, Long bankId, String reservationDate, String reservationTime) {
        this.customerId = customerId;
        this.bankerId = bankerId;
        this.taskId = taskId;
        this.bankId = bankId;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;

    }

}
