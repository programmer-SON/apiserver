package org.zerock.apiserver.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

// @RestController 에서 발생할 수 있는 예외처리
@RestControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> notExist(NoSuchElementException e){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("msg", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notExist(MethodArgumentNotValidException e){

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("msg", e.getMessage()));
    }
}
