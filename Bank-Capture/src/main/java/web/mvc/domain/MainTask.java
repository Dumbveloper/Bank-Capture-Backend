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
public class MainTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mainTaskId;  // 주업무 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banker_id")
    private Banker banker;  // 행원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;  // 업무

}
