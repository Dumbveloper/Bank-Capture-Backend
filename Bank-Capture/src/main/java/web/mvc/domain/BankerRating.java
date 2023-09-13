package web.mvc.domain;

import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Table(name = "bankerrating")
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
