package exception;

public class TestException extends RuntimeException{
    private String className;
    private ErrorCode errorCode;

    public TestException(ErrorCode errorcode) {
        this.errorCode = errorcode;
        this.className = this.getClass().getName();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
