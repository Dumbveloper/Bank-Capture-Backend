package web.mvc.dto.reservation;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
/**
 * /reservation/bankerinfo/
 * 예약하기 - 행원선택(행원정보)
 * BankerInfoResponseDTO 의 List<CertificationDTO> 에 들어가는 DTO
 */
public class CertificationDTO {
    private Long certificationId;  // 자격증 iD
    private String certificationName;  // 자격증 이름
}

