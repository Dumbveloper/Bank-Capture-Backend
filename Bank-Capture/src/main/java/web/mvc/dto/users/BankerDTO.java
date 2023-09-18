package web.mvc.dto.users;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
/**
 * /users/register
 * 회원가입
 */
public class BankerDTO {
    private Long bankerBankId;  // 소속 은행
    private String bankerName;  //행원 이름
    private String bankerEmail;  // 행원 이메일
    private String bankerPassword;  // 행원 비밀번호
    private String bankerCareer; // 행원 경력
    private String bankerImgPath;  //행원 사진
    private String bankerInfo;  //행원 한줄 소개
    private String bankerReviewFlag; //행원 리뷰 공개 여부 플래그
}
