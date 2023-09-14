package web.mvc.domain;

import lombok.*;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Table(name = "bankaveragestar")
@Immutable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BankerAverageStar {
    @Id
    private Long bankId; //은행 ID
    private Double avgStar; //평균 별점

}
