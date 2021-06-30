package exception;

public class BoardException extends DefaultException {
    public BoardException(ErrorCode errorcode) {
        super(errorcode);
    }
}
