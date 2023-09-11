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
public class BankerReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankerReviewId;  // primary key

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;  //예약 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banker_id")
    private Banker banker;  //행원 ID


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;  //고객 ID

    private String reviewDate;  //리뷰 작성 날짜
    private int starRating;  // 행원리뷰 별점
    private String comment;  // 행원리뷰 코멘트

}
