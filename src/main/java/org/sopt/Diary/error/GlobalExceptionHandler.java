package org.sopt.Diary.error;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex){
        ErrorResponse response = new ErrorResponse(ex.getErrorCode().getStatus(), ex.getErrorCode().getCode(), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(String.valueOf(ex.getErrorCode().getStatus())));
    }

}
