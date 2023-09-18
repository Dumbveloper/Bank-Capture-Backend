package web.mvc.dto.users;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
/**
 * /users/login
 * 행원 로그인 response
 */
public class BankerLoginResponseDTO {
    private Long bankerId;
    private Long bankId;
    private String bankerName;
}
