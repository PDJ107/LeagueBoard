package exception;

public class AuthException extends DefaultException {
    public AuthException(ErrorCode errorcode) {
        super(errorcode);
    }
}
