package com.colvir.spring_boot_homework.controller;

import com.colvir.spring_boot_homework.dto.ErrorResponse;
import com.colvir.spring_boot_homework.exception.GeneralException;
import com.colvir.spring_boot_homework.exception.RecordFoundException;
import com.colvir.spring_boot_homework.exception.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.colvir.spring_boot_homework.model.InternalErrorStatus.*;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(RECORD_DOES_NOT_EXIST, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RecordFoundException.class)
    public ResponseEntity<ErrorResponse> handleFoundException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(RECORD_IS_ALREADY_EXISTS, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FOUND);
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(GENERAL_EXCEPTION, e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
