package web.mvc.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonFormat
public enum ErrorCode implements EnumModel{

    //예시
    //회원 로그인 시 발생 가능 예외
    INVALID_Customer_Login(400, "C001", "존재하지 않는 고객 정보입니다."),
    INVALID_Customer_Password(400, "C002", "비밀번호가 일치하지 않습니다."),


    // 행원 로그인 시 발생 가능 예외
    INVALID_BANKER_Login(400,"B001","존재하지 않는 행원 정보입니다."),
    INVALID_BANKER_Password(400,"B002","비밀번호가 일치하지 않습니다."),

    //중복여부 체크
    DUPLICATE_CUSTOMER(400,"D001","중복된 이메일입니다.");

    private int status;
    private String code;
    private String message;

    ErrorCode(int status, String code, String message){
        this.status = status;
        this.code = code;
        this.message = message;

    }
    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String getValue() {
        return null;
    }
}
