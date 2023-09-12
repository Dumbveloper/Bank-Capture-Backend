package web.mvc.domain.review;

import lombok.*;
import web.mvc.domain.Banker;
import web.mvc.domain.Customer;
import web.mvc.domain.Reservation;

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
    private Reservation reservation; // 예약

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banker_id")
    private Banker banker;  //행원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;  //고객
    private String bankerReviewDate;  //리뷰 작성 날짜
    private int bankerStarRating;  // 행원리뷰 별점
    private String bankerReviewComment;  // 행원리뷰 코멘트

}
