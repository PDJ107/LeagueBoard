package exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    User_Not_Found("0", "존재하지 않는 유저입니다.", HttpStatus.BAD_REQUEST),
    Invalid_Request("1", "account 또는 password가 틀렸습니다.", HttpStatus.BAD_REQUEST),
    Account_Already_Exists("2", "이미 존재하는 Account 입니다.", HttpStatus.BAD_REQUEST);

    private String code;
    private String message;
    private HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
