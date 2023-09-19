package web.mvc.dto.sms;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SmsResponseDTO {
    String requestId;
    String requestTime;
    String statusCode;
    String statusName;
}