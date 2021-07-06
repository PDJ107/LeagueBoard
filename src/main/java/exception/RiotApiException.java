package exception;

import org.springframework.http.HttpStatus;

public class RiotApiException extends DefaultException{
    public RiotApiException(ErrorCode errorCode) {
        super(errorCode);
    }

    public RiotApiException(ErrorCode errorCode, HttpStatus httpStatus) {
        super(errorCode, httpStatus);
    }
}
