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
            de.setClassName(de.getStackTrace()[0].getClassName());
            return new ResponseEntity(e, de.getStatus());
        }
        else {
            Map<String, Object> res = new HashMap<>();
            res.put("message", e.getMessage());
            res.put("className", e.getStackTrace()[0].getClassName());
            res.put("exceptionName", e.getClass().getSimpleName());
            return new ResponseEntity(res, HttpStatus.BAD_REQUEST);
        }
    }
}
