package org.sopt.Diary.error;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private final HttpStatus httpStatus;
    private final String code;
    private final String error;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public ErrorResponse(HttpStatus httpStatus, String code, String error) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.error = error;
    }
}
