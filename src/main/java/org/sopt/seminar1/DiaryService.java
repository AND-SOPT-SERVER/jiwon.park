package org.sopt.seminar1;

import java.util.*;

public class DiaryService {

    private final DiaryRepository diaryRepository= new DiaryRepository();

    void writeDiary(final String body) {
        final Diary diary = new Diary(0L, body, false, 0);
        boolean success = diaryRepository.save(diary);
        if (!success) {
            System.out.println("일기 저장 중 오류 발생");
        }

    }

    List<Diary> getDiaryList(){
        return diaryRepository.findAll();
    }

     void deleteDiary(final long id) {
        diaryRepository.delete(id);
    }

     void update(final Diary updatedDiary) {
        diaryRepository.update(updatedDiary);
    }

     void restore(final long id) {
        diaryRepository.restore(id);
    }


    void updateAllDiaries() {
        List<Diary> diaryListAll = diaryRepository.getDiaryList();
        for (Diary diary : diaryListAll) {
            diary.setUpdateCount(0); // 모든 업데이트 카운트 초기화
        }
        diaryRepository.saveDiaryList(diaryListAll);
    }

}
