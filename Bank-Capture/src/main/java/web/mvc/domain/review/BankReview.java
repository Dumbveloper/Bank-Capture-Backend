package web.mvc.domain.review;

import lombok.*;
import web.mvc.domain.Bank;
import web.mvc.domain.Reservation;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BankReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankReviewId;  // primary key

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;  //예약

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private Bank bank;  //지점

    private int bankStarRating;  // 지점 리뷰 별점

}
