package web.mvc.dto.users;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
/**
 * /users/login
 * 행원 로그인
 */
public class BankerLoginRequestDTO {
    private String email;
    private String password;
}
