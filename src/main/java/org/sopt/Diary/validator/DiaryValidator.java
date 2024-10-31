package org.sopt.Diary.validator;

import org.springframework.stereotype.Component;

@Component
public class DiaryValidator {

    private final static int LENGTH_LIMIT = 30;

    public static void checkContent(String content) {
        if(content.length() > LENGTH_LIMIT){
            throw new IllegalArgumentException("글자 수는 30자를 넘을 수 없습니다");
        }
    }
}
