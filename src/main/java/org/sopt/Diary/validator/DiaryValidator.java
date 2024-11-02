package org.sopt.Diary.validator;

import org.sopt.Diary.error.CustomException;
import org.sopt.Diary.error.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class DiaryValidator {

    private final static int CONTENT_LIMIT = 30;
    private final static int TITLE_LIMIT = 10;

    public static void checkContent(String content) {
        if(content.length() > CONTENT_LIMIT || content.isEmpty()){
            throw new CustomException(ErrorCode.INVALID_INPUT_LENGTH);
        }
    }

    public static void checkTitle(String title) {
        if(title.length() > TITLE_LIMIT || title.isEmpty()){
            throw new CustomException(ErrorCode.INVALID_INPUT_LENGTH);
        }
    }
}
