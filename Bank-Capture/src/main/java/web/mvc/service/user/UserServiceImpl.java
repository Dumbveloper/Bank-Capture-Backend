package web.mvc.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.mvc.domain.Banker;
import web.mvc.domain.Customer;
import web.mvc.dto.users.BankerLoginRequestDTO;
import web.mvc.dto.users.CustomerDTO;
import web.mvc.dto.users.CustomerLoginRequestDTO;
import web.mvc.exception.CustomException;
import web.mvc.exception.ErrorCode;
import web.mvc.repository.BankerRepository;
import web.mvc.repository.CustomerRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankerRepository bankerRepository;

    @Override
    public String customerLogin(CustomerLoginRequestDTO customerLoginRequestDTO ) {

        //해당하는 회원이 있는지 체크
        if(customerRepository.findByCustomerEmail(customerLoginRequestDTO.getEmail()) == null)
            throw new CustomException(ErrorCode.INVALID_Customer_Login);



        Customer customer = customerRepository.
                                findByCustomerEmailAndCustomerPassword(customerLoginRequestDTO.getEmail(),
                                customerLoginRequestDTO.getPassword());

        //비밀번호 일치여부 체크
        if(customer == null)
            throw new CustomException(ErrorCode.INVALID_Customer_Password);

        return "success";

    }

    @Override
    public String bankerLogin(BankerLoginRequestDTO bankerLoginRequestDTO) {

        ////해당하는 회원이 있는지 체크
        if(bankerRepository.findByBankerEmail(bankerLoginRequestDTO.getEmail()) == null) {
            throw new CustomException(ErrorCode.INVALID_BANKER_Login);
        }

        Banker banker =bankerRepository.
                findByBankerEmailAndBankerPassword(bankerLoginRequestDTO.getEmail(),
                        bankerLoginRequestDTO.getPassword());

        //비밀번호 일치여부 체크
        if(banker == null) {
            throw new CustomException(ErrorCode.INVALID_BANKER_Password);
        }

        return "success";
    }

    @Override
    public String register(CustomerDTO customerDTO) {

        //이메일 중복여부 체크
        if(customerRepository.findByCustomerEmail(customerDTO.getCustomerEmail()) != null) {
            throw new CustomException(ErrorCode.DUPLICATE_CUSTOMER);
        }

        Customer customer = Customer.builder().
                customerEmail(customerDTO.getCustomerEmail()).
                customerPassword(customerDTO.getCustomerPassword()).
                customerPhone(customerDTO.getCustomerPhone()).
                customerName(customerDTO.getCustomerName()).
                build();

        //회원가입
        customerRepository.save(customer);

        return "success";

    }
}
