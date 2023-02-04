package com.mindex.challenge.exception;

import com.mindex.challenge.config.ErrorCodeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private ErrorCodeConfig errorCodeConfig;

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException e, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND.value(),
                errorCodeConfig.getNotFoundCode(),
                e.getMessage(), request.getDescription(false) );
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> badRequestException(BadRequestException e, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
                errorCodeConfig.getBadRequestCode(),
                e.getMessage(), request.getDescription(false) );
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }


    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalException(Exception e, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                errorCodeConfig.getInternalServerCode(),
                e.getMessage(), request.getDescription(false) );
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
