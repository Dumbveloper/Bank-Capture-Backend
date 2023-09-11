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
    private Customer customer; // 고객 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bankerId")
    private Banker banker;  // 행원 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskId")
    private Task task;  // 업무 ID
    private String date;  // 예약 날짜
    private String time;  // 예약 날짜
    private String finishFlag;  // 예약 완료 여부

//    @OneToOne(mappedBy = "reservation")
//    private AssetForm assetForm;
//
//    @OneToOne(mappedBy = "reservation")
//    private BankerReview bankerReview;
//
//    @OneToOne(mappedBy = "reservation")
//    private BankReview bankReview;
//
//    @OneToOne(mappedBy = "reservation")
//    private BussinessLoanForm bussinessLoanForm;
//
//    @OneToOne(mappedBy = "reservation")
//    private PersonalLoanForm personalLoanForm;


}
