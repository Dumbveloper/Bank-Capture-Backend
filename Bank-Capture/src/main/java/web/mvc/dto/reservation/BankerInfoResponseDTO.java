package web.mvc.dto.reservation;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
/**
 * /reservation/bankerinfo/
 * 예약하기 - 행원선택(행원정보)
 */
public class BankerInfoResponseDTO {
    private List<CertificationDTO> certificationList = new ArrayList<>();
    private List<BankerReviewDTO> bankerReviewList = new ArrayList<>();


}
