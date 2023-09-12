package web.mvc.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;  // 예약 iD

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    private Customer customer; // 고객

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bankerId")
    private Banker banker;  // 행원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskId")
    private Task task;  // 업무
    private String reservationDate;  // 예약 날짜
    private String reservationTime;  // 예약 시간
    private String reservationFinishFlag;  // 예약 완료 여부

}
