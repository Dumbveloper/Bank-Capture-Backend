package web.mvc.controller.user;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import web.mvc.dto.users.BankerLoginRequestDTO;
import web.mvc.dto.users.CustomerDTO;
import web.mvc.dto.users.CustomerLoginRequestDTO;
import web.mvc.service.user.UserService;

@Api(tags = "회원관리 API")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "회원 로그인", notes = "이메일과 비밀번호로 로그인을 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공"),
            @ApiResponse(code = 500, message = "에러")
    })
    @ApiParam(value = "회원 로그인 정보 객체", required = true)
    @PostMapping("/customer-login")
    public String customerLogin(@RequestBody CustomerLoginRequestDTO customerLoginRequestDTO){
        System.out.println("유저로그인");
        return userService.customerLogin(customerLoginRequestDTO);
    }

    @ApiOperation(value = "행원 로그인", notes = "이메일과 비밀번호로 로그인을 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공"),
            @ApiResponse(code = 500, message = "에러")
    })
    @ApiParam(value = "행원 로그인 정보 객체", required = true)
    @PostMapping("/banker-login")
    public String bankerLogin(@RequestBody BankerLoginRequestDTO bankerLoginRequestDTO){
        System.out.println(bankerLoginRequestDTO.getEmail());
        System.out.println(bankerLoginRequestDTO.getPassword());
        System.out.println("행원로그인");
        return userService.bankerLogin(bankerLoginRequestDTO);
    }

    @ApiOperation(value = "회원가입", notes = "이름, 이메일, 비밀번호, 전화번호를 입력하여 회원가입을 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원가입성공"),
            @ApiResponse(code = 500, message = "에러")
    })
    @ApiParam(value = "회원가입 정보 객체", required = true)
    @PostMapping("/register")
    public String register(@RequestBody CustomerDTO customerDTO){
        return userService.register(customerDTO);
    }
}
