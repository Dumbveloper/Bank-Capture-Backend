package web.mvc.dto.reservation;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
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
    private double avgstar;//평균별점

    public BankDTO (Long bankId, String bankName,double locationX,double locationY,String bankPhone,String bankAddr){
        this.bankId=bankId;
        this.bankName=bankName;
        this.locationX=locationX;
        this.locationY=locationY;
        this.bankPhone=bankPhone;
        this.bankAddr=bankAddr;
    }
}
