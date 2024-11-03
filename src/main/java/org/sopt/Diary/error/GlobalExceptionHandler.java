package org.sopt.Diary.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
전역적으로 에러를 처리해준다.
@ControllerAdvice 에 @ResponseBody 추가 된 형태
@ControllerAdvice는 @ExceptionHandler 에 기능을 부여해주는 역할
*/
@RestControllerAdvice
public class GlobalExceptionHandler {

    //특정 에러 발생 시 Controller에 발생하였을 경우 해당 에러를 캐치하여 클라이언트로 오류를 반환하도록 처리하는 기능을 수행
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex){
        ErrorResponse response = new ErrorResponse(
                ex.getErrorCode().getStatus(),
                ex.getErrorCode().getCode(),
                ex.getErrorCode().getMessage());
        return new ResponseEntity<>(response,ex.getErrorCode().getStatus());
    }
}
