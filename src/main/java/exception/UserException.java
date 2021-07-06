package exception;

import org.springframework.http.HttpStatus;

public class UserException extends DefaultException {
    public UserException(ErrorCode errorcode) {
        super(errorcode);
    }

    public UserException(ErrorCode errorCode, HttpStatus httpStatus) {
        super(errorCode, httpStatus);
    }
}
