package org.sopt.seminar1;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DiaryFormatter {

    private static final int LENGTH =4;

    //Diary 객체를 String 형식으로 변환
    String diaryToString(final Diary diary) {
        return diary.getId() + "/" +
                diary.getBody() + "/" +
                diary.getIsDeleted() + "/" +
                diary.getUpdateCount();
    }

    //문자열 리스트를 Diary 객체 리스트로 변환
    public List<Diary> parseDiaryList(List<String> lines) {
        List<Diary> diaries = new ArrayList<>();
        for (String line : lines) {
            Diary diary = parseDiary(line);
            if (diary != null) {
                diaries.add(diary);
            }
        }
        return diaries;
    }

    //단일 문자열을 Diary 객체로 변환
    private Diary parseDiary(String line) {
        String[] data = line.split("/");
        if (data.length == LENGTH) {
            try {
                long id = Long.parseLong(data[0]);
                String body = data[1];
                boolean isDeleted = Boolean.parseBoolean(data[2]);
                int updateCount = Integer.parseInt(data[3]);
                return new Diary(id, body, isDeleted, updateCount);
            } catch (NumberFormatException e) {
                System.out.println("잘못된 형식의 데이터가 있습니다: " + line);
            }
        } else {
            System.out.println("데이터 형식이 올바르지 않습니다: " + line);
        }
        return null;
    }

}
