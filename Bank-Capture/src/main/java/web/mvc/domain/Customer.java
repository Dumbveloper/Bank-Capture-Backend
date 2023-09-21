package web.mvc.domain;

import lombok.*;
import org.springframework.stereotype.Component;
import web.mvc.dto.users.CustomerDTO;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;  //고객 ID
    private String customerName;  // 고객 이름
    private String customerEmail;  // 고객 이메일
    private String customerPassword;  //고객 비밀번호
    private String customerPhone;  //고객 전화번호

    public static Customer toEntity(CustomerDTO customerDTO) {
        Customer customer = Customer.builder().
                customerEmail(customerDTO.getCustomerEmail()).
                customerPassword(customerDTO.getCustomerPassword()).
                customerPhone(customerDTO.getCustomerPhone()).
                customerName(customerDTO.getCustomerName()).
                build();
        return customer;
    }


}
