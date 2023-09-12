package web.mvc.service.user;

import web.mvc.dto.users.CustomerDTO;

import java.util.List;

public interface UserService {

    /**
     * /users/login
     * 로그인
     * CustomerRepository
     *
     * @param email
     * @param password
     * @return String 성공여부
     */
    public String customerLogin(String email, String password);

    /**
     * /users/login
     * 로그인
     * BankerRepository
     *
     * @param email
     * @param password
     * @return String 성공여부
     */
    public String BankerLogin(String email, String password);

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
