package web.mvc.dto.users;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
/**
 * /users/login
 * 고객 로그인 response
 */
public class CustomerLoginResponseDTO {
    private Long customerId;
    private String customerName;
}
