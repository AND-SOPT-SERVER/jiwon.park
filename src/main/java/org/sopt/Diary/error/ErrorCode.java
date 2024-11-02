package org.sopt.Diary.error;

import org.springframework.http.HttpStatus;


public enum ErrorCode {

    //user
    ALREADY_EXIST_USER(HttpStatus.CONFLICT, "USER001", "이미 존재하는 사용자입니다."),
    INVALID_USER(HttpStatus.NOT_FOUND, "USER002", "존재하지 않는 사용자입니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "USER004", "로그인 정보가 올바르지 않습니다."),

    //diary
    INVALID_INPUT_LENGTH(HttpStatus.LENGTH_REQUIRED, "diary001", "글자수를 다시 확인해주세요"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "diary002", "접근 권한이 없습니다"),
    DIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "diary003", "일기를 찾을 수 없습니다."),
    DUPLICATE_TITLE(HttpStatus.NOT_FOUND, "diary004", "이미 있는 제목입니다.");



    private HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
