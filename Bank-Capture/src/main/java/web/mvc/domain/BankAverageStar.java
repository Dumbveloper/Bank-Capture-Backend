package web.mvc.domain;

import lombok.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;

@Entity
@Subselect("SELECT \n" +
        "        subquery.bank_id AS bank_id,\n" +
        "        ROUND(subquery.avg_star, 2) AS avg_star\n" +
        "    FROM\n" +
        "        (SELECT \n" +
        "            reservation.bank_id AS bank_id,\n" +
        "                AVG(reservation.bank_star_rating) AS avg_star\n" +
        "        FROM\n" +
        "            reservation\n" +
        "        WHERE\n" +
        "            (reservation.bank_star_rating > 0)\n" +
        "        GROUP BY reservation.bank_id) subquery")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BankAverageStar {
    @Id
    private Long bankId; //은행 ID
    private Double avgStar; //평균 별점

}
