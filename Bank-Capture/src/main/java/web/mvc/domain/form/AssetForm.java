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
public class AssetForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assetFormId;  // primary key

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;  //예약 ID
    private int yearIncome; // 연소득수준
    private String assetLocation; //물건소재지
    private String homeCategory; //주택구분
    private String homeFlag;  //주택보유여부
    private String loanFlag;  //주택관련대출보유여부
    private String loanPurpose;  //대출용도
    private int wishAmount; //대출 희망금액
}
