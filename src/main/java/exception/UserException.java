package exception;

public class UserException extends DefaultException {
    public UserException(ErrorCode errorcode) {
        super(errorcode);
    }
}
