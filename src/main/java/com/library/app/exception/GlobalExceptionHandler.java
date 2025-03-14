package com.library.app.exception;

/*
 * Author: Francesca Murano
 */

import com.library.app.dto.ResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ResponseDTO<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ResponseDTO<Object> response = new ResponseDTO<>(false, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    ResponseEntity<ResponseDTO<Object>> handleDuplicateEntityException(DuplicateEntityException ex){
        ResponseDTO<Object> response = new ResponseDTO<>(false, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<ResponseDTO<Object>> handleEntityNotFoundException(EntityNotFoundException ex){
        ResponseDTO<Object> response = new ResponseDTO<>(false, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ResponseDTO<Object>> handleEntityException(EntityNotFoundException ex){
        ResponseDTO<Object> response = new ResponseDTO<>(false, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);    }

}