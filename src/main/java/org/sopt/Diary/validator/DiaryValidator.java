package org.sopt.Diary.validator;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class DiaryValidator {

    private final static int CONTENT_LIMIT = 30;
    private final static int TITLE_LIMIT = 10;

    public static void checkContent(String content) {
        if(content.length() > CONTENT_LIMIT || content.isEmpty()){
            throw new ResponseStatusException(HttpStatus.LENGTH_REQUIRED, "내용 글자 수는 1~30자가 되어야 합니다");
        }
    }

    public static void checkTitle(String title) {
        if(title.length() > TITLE_LIMIT || title.isEmpty()){
            throw new ResponseStatusException(HttpStatus.LENGTH_REQUIRED, "제목 글자 수는 1~10자가 되어야 합니다");
        }
    }
}
