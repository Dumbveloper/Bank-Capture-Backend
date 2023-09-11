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
public class BussinessLoanForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bussinessLoanFormId;  // primary key

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;  //예약 ID
    private int yearIncome; // 연소득수준
    private String assetLoacion; //물건소재지
    private String homeCategory; //주택구분
    private String homeFlag;  //주택보유여부
    private String loanFlag;  //주택관련대출보유여부
    private String loanPurpose;  //대출용도
    private int wishAmount; //대출 희망금액
}
