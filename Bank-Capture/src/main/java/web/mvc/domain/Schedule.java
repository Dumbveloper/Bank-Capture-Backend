package web.mvc.domain;

import lombok.*;
import web.mvc.dto.reservation.ScheduleDTO;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"bank","banker"})
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;  // 스케줄 iD

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bankerId")
    private Banker banker;  // 행원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bankId")
    private Bank bank;  // 지점

    private String scheduleDate;  // 스케줄 날짜
    private int time1;  // 1부  9시
    private int time2;  // 2부  10시
    private int time3;  // 3부  11시
    private int time4;  // 4부  13시
    private int time5;  // 5부  14시
    private int time6;  // 6부  15시
    private int time7;  // 7부  16시

    public static Schedule toEntity(Banker insertBanker, Bank insertBank, ScheduleDTO scheduleDTO) {
        Schedule insertSchedule = Schedule.builder()
                .banker(insertBanker)
                .bank(insertBank)
                .scheduleDate(scheduleDTO.getScheduleDate())
                .time1(scheduleDTO.getTime1())
                .time2(scheduleDTO.getTime2())
                .time3(scheduleDTO.getTime3())
                .time4(scheduleDTO.getTime4())
                .time5(scheduleDTO.getTime5())
                .time6(scheduleDTO.getTime6())
                .time7(scheduleDTO.getTime7()).build();
        return insertSchedule;
    }



}
