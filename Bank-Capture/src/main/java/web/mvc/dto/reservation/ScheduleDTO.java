package web.mvc.dto.reservation;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
/**
 * /myPage/banker/checktime
 * 마이페이지(행원) - 예약가능한 시간 체크
 *
 * /reservation/bankerAll
 * 예약하기 - 예약가능한 행원 조회
 * BankerAllResponseDTO의 List<ScheduleDTO>에 들어가는 DTO
 */
public class ScheduleDTO {
    private Long bankerId; // 행원 id
    private Long bankId; // 은행 id
    private String scheduleDate;  // 스케줄 날짜
    private int time1;  // 1부  9시
    private int time2;  // 2부  10시
    private int time3;  // 3부  11시
    private int time4;  // 4부  13시
    private int time5;  // 5부  14시
    private int time6;  // 6부  15시
    private int time7;  // 7부  16시
}
