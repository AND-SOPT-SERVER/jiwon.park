package org.sopt.seminar1;

import org.springframework.stereotype.Component;

@Component
public class DiaryValidator {
    private final static int MAX_LENGTH = 30;

    boolean validation(final String body) {

        if (body.length() > MAX_LENGTH || body.trim().isBlank()) {
            System.out.println("1~30 글자수를 맞춰주세요");
            return false;
        }
        return true;
    }

}
