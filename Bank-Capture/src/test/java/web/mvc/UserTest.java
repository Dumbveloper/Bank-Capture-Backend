package web.mvc;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.domain.Customer;
import web.mvc.dto.users.CustomerDTO;
import web.mvc.dto.users.CustomerLoginRequestDTO;
import web.mvc.dto.users.CustomerLoginResponseDTO;
import web.mvc.repository.CustomerRepository;
import web.mvc.service.user.UserService;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserTest {
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void 회원가입_서비스() {
        //given
        CustomerDTO customerDTO = new CustomerDTO("원식", "wonsik1@naver.com", "1234", "010-0000-1111");
        //when
        String result = userService.register(customerDTO);
        //then
        assertEquals("success",result);
    }

    @Test
    public void 회원가입_레포지토리() throws Exception{
        //given
        Customer customer = new Customer();
        customer.setCustomerName("봉섭");
        //when
        Long savedId = customerRepository.save(customer).getCustomerId();
        //then
        assertEquals(customer, customerRepository.findById(savedId).get());
        //findById 메소드는 반환타입이 Optional<Customer> 임! get() 메소드로 뽑아야 비교가능
    }


    @Test
    public void 로그인시도_오류() throws Exception{
        //given
        CustomerDTO customerDTO = new CustomerDTO("원식", "wonsik1@naver.com", "1234", "010-0000-1111");
        userService.register(customerDTO); // 회원가입

        //when
        assertThrows(RuntimeException.class, () -> {
            CustomerLoginRequestDTO customerLoginRequestDTO = new CustomerLoginRequestDTO("qhdtjq@naver", "1234"); // 잘못된 이메일로 로그인 시도
            userService.customerLogin(customerLoginRequestDTO);
        });

    }

    @Test
    public void 중복_회원가입_예외() throws Exception{
        //given 동일한 이메일(아이디) 값
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setCustomerEmail("song@naver.com");
        CustomerDTO customer2 = new CustomerDTO();
        customer2.setCustomerEmail("song@naver.com");
        //when , then
        assertThrows(RuntimeException.class, () -> {
            userService.register(customer1);
            userService.register(customer2);
        });
    }
}
