package web.mvc.service.user;

import web.mvc.dto.users.BankerLoginRequestDTO;
import web.mvc.dto.users.CustomerDTO;
import web.mvc.dto.users.CustomerLoginRequestDTO;

import java.util.List;

public interface UserService {

    /**
     * /users/login
     * 로그인
     * CustomerRepository
     *
     * @param customerLoginRequestDTO
     * @return String 성공여부
     * @return String 성공여부
     */
    public String customerLogin(CustomerLoginRequestDTO customerLoginRequestDTO);

    /**
     * /users/login
     * 로그인
     * BankerRepository
     *
     * @param bankerLoginRequestDTO
     * @return String 성공여부
     */
    public String bankerLogin(BankerLoginRequestDTO bankerLoginRequestDTO);

    /**
     * /users/register
     * 회원가입
     * CustomerRepository
     *
     * @param customerDTO
     * @return String 성공여부
     */
    public String register(CustomerDTO customerDTO);


}
