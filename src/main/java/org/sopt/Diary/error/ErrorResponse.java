package org.sopt.Diary.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final HttpStatus httpStatus;
    private final String code;
    private final String error;
}
