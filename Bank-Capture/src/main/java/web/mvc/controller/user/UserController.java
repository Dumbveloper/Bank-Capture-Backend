package web.mvc.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import web.mvc.dto.users.BankerLoginRequestDTO;
import web.mvc.dto.users.CustomerDTO;
import web.mvc.dto.users.CustomerLoginRequestDTO;
import web.mvc.service.user.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/customer-login")
    public String customerLogin(@RequestBody CustomerLoginRequestDTO customerLoginRequestDTO){
        System.out.println("유저로그인");
        return userService.customerLogin(customerLoginRequestDTO);
    }

    @PostMapping("/banker-login")
    public String bankerLogin(@RequestBody BankerLoginRequestDTO bankerLoginRequestDTO){
        System.out.println(bankerLoginRequestDTO.getEmail());
        System.out.println(bankerLoginRequestDTO.getPassword());
        System.out.println("행원로그인");
        return userService.bankerLogin(bankerLoginRequestDTO);
    }

    @PostMapping("/register")
    public String register(@RequestBody CustomerDTO customerDTO){
        return userService.register(customerDTO);
    }
}
