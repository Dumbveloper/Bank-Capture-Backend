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
public class BankerCertification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankerCertificationId;  // 보유자격증 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bankerId")
    private Banker banker;  // 행원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificationId")
    private Certification certification;  // 자격증

}
