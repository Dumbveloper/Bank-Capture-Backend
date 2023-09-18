package web.mvc.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.mvc.domain.Bank;
import web.mvc.domain.Banker;
import web.mvc.domain.Customer;
import web.mvc.dto.users.*;
import web.mvc.encryption.EncryptHelper;
import web.mvc.exception.CustomException;
import web.mvc.exception.ErrorCode;
import web.mvc.repository.BankRepository;
import web.mvc.repository.BankerRepository;
import web.mvc.repository.CustomerRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BankerRepository bankerRepository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private EncryptHelper encryptHelper;

    @Override
    public CustomerLoginResponseDTO customerLogin(CustomerLoginRequestDTO customerLoginRequestDTO ) {

        //해당하는 회원이 있는지 체크
        Customer byCustomerEmail = customerRepository.findByCustomerEmail(customerLoginRequestDTO.getEmail());
        if(byCustomerEmail == null)
            throw new CustomException(ErrorCode.INVALID_Customer_Login);

        String encrypted = byCustomerEmail.getCustomerPassword(); // pwd 암호화되어 저장되어 있음
        boolean result = encryptHelper.isMatch(customerLoginRequestDTO.getPassword(), encrypted);

        //비밀번호 일치여부 체크
        if(!result)
            throw new CustomException(ErrorCode.INVALID_Customer_Password);

        CustomerLoginResponseDTO customerLoginResponseDTO = new CustomerLoginResponseDTO(byCustomerEmail.getCustomerId(), byCustomerEmail.getCustomerName());

        return customerLoginResponseDTO;

    }

    @Override
    public BankerLoginResponseDTO bankerLogin(BankerLoginRequestDTO bankerLoginRequestDTO) {

        ////해당하는 회원이 있는지 체크
        Banker byBankerEmail = bankerRepository.findByBankerEmail(bankerLoginRequestDTO.getEmail());
        if(byBankerEmail == null) {
            throw new CustomException(ErrorCode.INVALID_BANKER_Login);
        }

        String encrypted = byBankerEmail.getBankerPassword();
        boolean result = encryptHelper.isMatch(bankerLoginRequestDTO.getPassword(), encrypted);


        //비밀번호 일치여부 체크
        if(!result) {
            throw new CustomException(ErrorCode.INVALID_BANKER_Password);
        }

        BankerLoginResponseDTO bankerLoginResponseDTO = new BankerLoginResponseDTO(byBankerEmail.getBankerId(),byBankerEmail.getBank().getBankId() ,byBankerEmail.getBankerName());

        return bankerLoginResponseDTO;
    }

    @Override
    public String register(CustomerDTO customerDTO) {

        //이메일 중복여부 체크
        if(customerRepository.findByCustomerEmail(customerDTO.getCustomerEmail()) != null) {
            throw new CustomException(ErrorCode.DUPLICATE_CUSTOMER);
        }

        //회원가입 비밀번호 암호화
        String dtoPassword = customerDTO.getCustomerPassword(); // dto 비밀번호 꺼내서 암호화
        String encrypted = encryptHelper.encrypt(dtoPassword);
        customerDTO.setCustomerPassword(encrypted);

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

    @Override
    public String registerBanker(BankerDTO bankerDTO) {


        //이메일 중복여부 체크
        if(bankerRepository.findByBankerEmail(bankerDTO.getBankerEmail()) != null) {
            throw new CustomException(ErrorCode.DUPLICATE_CUSTOMER);
        }

        //회원가입 비밀번호 암호화
        String dtoPassword = bankerDTO.getBankerPassword(); // dto 비밀번호 꺼내서 암호화
        String encrypted = encryptHelper.encrypt(dtoPassword);
        bankerDTO.setBankerPassword(encrypted);

        Optional<Bank> bank = bankRepository.findById(bankerDTO.getBankerBankId());

        Banker banker = Banker.builder().
                bank(bank.get()).
                bankerName(bankerDTO.getBankerName()).
                bankerEmail(bankerDTO.getBankerEmail()).
                bankerPassword(bankerDTO.getBankerPassword()).
                bankerCareer(bankerDTO.getBankerCareer()).
                bankerImgPath(bankerDTO.getBankerImgPath()).
                bankerInfo(bankerDTO.getBankerInfo()).
                bankerReviewFlag(bankerDTO.getBankerReviewFlag()).
                build();

        //회원가입
        bankerRepository.save(banker);

        return "success";

    }
}
