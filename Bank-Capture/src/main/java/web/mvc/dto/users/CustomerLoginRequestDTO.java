package web.mvc.dto.users;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
/**
 * /users/login
 * 고객 로그인
 */
public class CustomerLoginRequestDTO {
    private String email;
    private String password;
}
