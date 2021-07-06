package controller;

import exception.DefaultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Throwable.class)
    public ResponseEntity errorHandling(Throwable e) {
        if(e instanceof DefaultException) {
            DefaultException de = (DefaultException)e;
            de.setDetail(de.getStackTrace()[0].getClassName() + "." +
                    de.getStackTrace()[0].getMethodName() + "(Line:" +
                    de.getStackTrace()[0].getLineNumber() + ")");
            return new ResponseEntity(de, de.getStatus());
        }
        else {
            Map<String, Object> res = new HashMap<>();
            res.put("message", e.getMessage());
            res.put("detail", e.getStackTrace()[0].getClassName() + "." +
                    e.getStackTrace()[0].getMethodName() + "(Line:" +
                    e.getStackTrace()[0].getLineNumber() + ")");
            res.put("exceptionName", e.getClass().getSimpleName());
            return new ResponseEntity(res, HttpStatus.BAD_REQUEST);
        }
    }
}
