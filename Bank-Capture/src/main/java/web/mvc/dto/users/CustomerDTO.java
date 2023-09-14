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
public class CustomerDTO {
    private String customerName;  // 고객 이름
    private String customerEmail;  // 고객 이메일
    private String customerPassword;  //고객 비밀번호
    private String customerPhone;  //고객 전화번호
}
