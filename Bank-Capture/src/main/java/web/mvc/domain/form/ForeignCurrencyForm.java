package web.mvc.domain.form;

import lombok.*;
import web.mvc.domain.Reservation;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ForeignCurrencyForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foreignCurrencyFormId;  // primary key

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;  //예약 ID
    private int monthlyIncome; //월수입
    private String job; //직업군
    private String investJoinFlag; //투자상품가입경험
    private int investAmount;  //투자금액
    private int investPeriod;  //투자기관
    private double targetReturn;  //목표수익률
}