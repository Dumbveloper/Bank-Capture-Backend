package web.mvc.dto.reservation;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
/**
 * /reservation/marker
 * 지점 마커 상세
 */
public class BankDTO {
    private Long bankId;  //지점 ID
    private String bankName; // 지점이름
    private double locationX; // 지점X좌표
    private double locationY; // 지점Y좌표
    private String bankPhone; // 지점전화번호
    private String bankAddr; // 지점주소
}
