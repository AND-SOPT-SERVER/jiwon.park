package org.sopt.seminar1;

import java.io.IOException;
import java.util.List;

public class DiaryService {

    private final DiaryRepository diaryRepository= new DiaryRepository();

    void writeDiary(final String body) {
        final Diary diary = new Diary(0L, body, false, 0);
        boolean success = diaryRepository.save(diary);
        if (!success) {
            System.out.println("일기 저장 중 오류 발생. 다시 시도해 주세요.");
        }

    }

    List<Diary> getDiaryList(){
        return diaryRepository.findAll();
    }

     void delete(String id) {
        long ID = Long.parseLong(id);
        diaryRepository.delete(ID);
    }

     void update(String id, String body) {
        long ID = Long.parseLong(id);
        final Diary diary = new Diary(ID, body,false,+1);
        diaryRepository.update(diary);
    }

     void restore(final String id) {
        long ID = Long.parseLong(id);
         diaryRepository.restore(ID);
    }


}
