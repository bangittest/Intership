package com.rk.advice;

import com.rk.exception.CustomException;
import com.rk.exception.ImportDataException;
import com.rk.exception.WareHouseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> invalidRequest(MethodArgumentNotValidException e){
        Map<String,String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(),fieldError.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> customException(CustomException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WareHouseException.class)
    public ResponseEntity<String> WareHouseException(WareHouseException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImportDataException.class)
    public ResponseEntity<String> ImportDataException(ImportDataException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }


}
