package exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import response.ErrorResponse;

@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(TestException.class)
    public ResponseEntity<Object> errorHandling(TestException e) {
        ErrorResponse res = new ErrorResponse(e.getErrorCode());
        res.setClassName(e.getClassName());
        return new ResponseEntity<>(res, res.getStatus());
    }
}
