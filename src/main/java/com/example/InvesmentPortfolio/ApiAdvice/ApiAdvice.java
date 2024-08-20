package com.example.InvesmentPortfolio.ApiAdvice;

import com.example.InvesmentPortfolio.Api.ApiException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ApiAdvice {
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity ApiResponse(ApiException ex){
        String message = ex.getMessage();
        return  ResponseEntity.status(400).body(message);
    }
    @ExceptionHandler(value = DataIntegrityViolationException.class) //duplicate
    public ResponseEntity DataIntegrityViolationException(DataIntegrityViolationException ex){
        String message = ex.getMessage();
        return  ResponseEntity.status(400).body(message);
    }
    @ExceptionHandler(value = EntityNotFoundException.class) //ask for no exist id
    public ResponseEntity EntityNotFoundException(EntityNotFoundException ex){
        String message = ex.getMessage();
        return  ResponseEntity.status(404).body(message);
    }
    @ExceptionHandler(value = HttpMessageNotReadableException.class) //wrong date
    public ResponseEntity HttpMessageNotReadableException(HttpMessageNotReadableException ex){
        String message = ex.getMessage();
        return  ResponseEntity.status(400).body(message);
    }
    @ExceptionHandler(value = NoResourceFoundException.class) //get for no exist id //wrong path
    public ResponseEntity NoResourceFoundException(NoResourceFoundException ex){
        String message = ex.getMessage();
        return  ResponseEntity.status(404).body(message);
    }
    @ExceptionHandler(value = IllegalArgumentException.class) //wrong date validate
    public ResponseEntity IllegalArgumentException(IllegalArgumentException ex){
        String message = ex.getMessage();
        return  ResponseEntity.status(400).body(message);
    }
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class) //update for get but the right post
    public ResponseEntity HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex){
        String message = ex.getMessage();
        return  ResponseEntity.status(400).body(message);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class) //wrong trnscationType
    public ResponseEntity MethodArgumentNotValidException(MethodArgumentNotValidException ex){
        String message = ex.getMessage();
        return  ResponseEntity.status(400).body(message);
    }
    @ExceptionHandler(value = RuntimeException.class) //delete entity
    public ResponseEntity RuntimeException(RuntimeException ex){
        String message = ex.getMessage();
        return  ResponseEntity.status(500).body(message);
    }

}

