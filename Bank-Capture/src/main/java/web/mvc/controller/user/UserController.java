package web.mvc.controller.user;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import web.mvc.dto.users.*;
import web.mvc.service.user.UserService;

@Api(tags = "회원관리 API")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원 로그인", notes = "이메일과 비밀번호로 로그인을 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공"),
            @ApiResponse(code = 500, message = "에러")
    })
    @ApiParam(value = "회원 로그인 정보 객체", required = true)
    @PostMapping("/customer-login")
    public CustomerLoginResponseDTO customerLogin(@RequestBody CustomerLoginRequestDTO customerLoginRequestDTO){
        return userService.customerLogin(customerLoginRequestDTO);
    }

    @ApiOperation(value = "행원 로그인", notes = "이메일과 비밀번호로 로그인을 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공"),
            @ApiResponse(code = 500, message = "에러")
    })
    @ApiParam(value = "행원 로그인 정보 객체", required = true)
    @PostMapping("/banker-login")
    public BankerLoginResponseDTO bankerLogin(@RequestBody BankerLoginRequestDTO bankerLoginRequestDTO){
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
