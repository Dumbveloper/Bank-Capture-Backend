package web.mvc.dto.reservation;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
/**
 * /reservation/bankerAll
 * 예약하기 - 예약가능한 행원 조회
 * BankerAllResponseDTO의 List<MainTaskDTO>에 들어가는 DTO
 */
public class TaskDTO {
    private String taskName; // 업무 이름
}
