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
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "certification_seq")
    @SequenceGenerator(name = "certification_seq", allocationSize = 1, sequenceName = "certification_seq")
    private Long certificationId;  // 자격증 iD
    
    private String name;  // 자격증 이름



}
