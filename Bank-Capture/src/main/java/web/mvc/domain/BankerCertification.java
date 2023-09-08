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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bankerCertification_seq")
    @SequenceGenerator(name = "bankerCertification_seq", allocationSize = 1, sequenceName = "bankerCertification_seq")
    private Long bankerCertificationId;  // 보유자격증 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banker_id")
    private Banker banker;  // 행원 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certification_id")
    private Certification certification_id;  // 자격증 ID



}
