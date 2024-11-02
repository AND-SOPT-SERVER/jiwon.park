package org.sopt.Diary.error;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    // 서비스 레이어에서 주로 사용하기 위한 예외
    // HTTP 응답에 직접적으로 영향을 미치지 않기 때문에, 구체적인 예외 처리 정의 후 처리

    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
