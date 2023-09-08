package web.mvc.domain;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name = "customer_seq", allocationSize = 1, sequenceName = "customer_seq")
    private Long customerId;  //고객 ID
    private String name;  // 고객 이름
    private String email;  // 고객 이메일
    private String password;  //고객 비밀번호
    private String phone;  //고객 전화번호


}
