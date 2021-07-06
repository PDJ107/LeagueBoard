package exception;

import org.springframework.http.HttpStatus;

public class AuthException extends DefaultException {
    public AuthException(ErrorCode errorcode) {
        super(errorcode);
    }

    public AuthException(ErrorCode errorCode, HttpStatus httpStatus) {
        super(errorCode, httpStatus);
    }
}
