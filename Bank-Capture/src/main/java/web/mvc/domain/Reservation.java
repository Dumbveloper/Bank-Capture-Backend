package web.mvc.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@DynamicInsert
@DynamicUpdate
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
    @JoinColumn(name = "bankId")
    private Bank bank;  // 지점

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskId")
    private Task task;  // 업무
    private String reservationDate;  // 예약 날짜
    private String reservationTime;  // 예약 시간
    @ColumnDefault("'F'")
    private String reservationFinishFlag ;  // 예약 완료 여부
    private int bankerStarRating;  //행원리뷰 평점
    private int bankStarRating;  //지점리뷰 평점
    private String comment;  //행원리뷰 코멘트


}
