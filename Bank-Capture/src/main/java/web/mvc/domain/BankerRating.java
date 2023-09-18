package web.mvc.domain;

import lombok.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;

@Entity
@Subselect("SELECT\n" +
        "\tbank_id,\n" +
        "    banker_id,\n" +
        "    ROUND(AVG(banker_star_rating),2) AS avg_star,\n" +
        "    count(reservation.comment) as cnt_comment\n" +
        "from\n" +
        "    reservation\n" +
        "WHERE\n" +
        "    banker_star_rating > 0\n" +
        "Group by bank_id,banker_id")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BankerRating {
    @Id
    private Long bankerId;  //행원 ID

    private Long bankId; //은행 ID
    private Double avgStar; //평균 별점
    private Long cntComment; //리뷰 개수

    @OneToOne
    @JoinColumn(name = "bankerId", referencedColumnName = "id", insertable = false, updatable = false)
    private Banker banker;

}
