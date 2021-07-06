package exception;

import org.springframework.http.HttpStatus;

public class BoardException extends DefaultException {
    public BoardException(ErrorCode errorcode) {
        super(errorcode);
    }

    public BoardException(ErrorCode errorCode, HttpStatus httpStatus) {
        super(errorCode, httpStatus);
    }
}
