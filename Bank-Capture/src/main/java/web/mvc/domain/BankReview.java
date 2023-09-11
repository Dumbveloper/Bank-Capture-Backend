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
public class BankReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankReviewId;  // primary key

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;  //예약 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private Bank bank;  //지점 ID

    private int starRating;  // 지점 리뷰 별점

}
