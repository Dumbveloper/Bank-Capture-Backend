package web.mvc.dto.sms;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class MessageDTO {
    String to;
    String subject;
    String content;
}