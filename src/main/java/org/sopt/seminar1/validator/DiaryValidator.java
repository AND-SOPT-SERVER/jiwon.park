package org.sopt.seminar1.validator;

import org.springframework.stereotype.Component;

import java.text.BreakIterator;

@Component
public class DiaryValidator {
    private final static int MAX_LENGTH = 30;

    private int getGraphemeLength(String value) {
        BreakIterator it = BreakIterator.getCharacterInstance();
        it.setText(value);
        int count = 0;
        while (it.next() != BreakIterator.DONE) {
            count++;
        }
        return count;
    }


    public boolean validation(final String body) {

        int length = getGraphemeLength(body);

        if (length > MAX_LENGTH || body.trim().isBlank()) {
            System.out.println("1~30 글자수를 맞춰주세요!");
            return false;
        }
        return true;
    }

}
