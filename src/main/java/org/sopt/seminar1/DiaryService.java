package org.sopt.seminar1;

import java.util.List;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();

    void writeDiary(final String body){
        final Diary diary = new Diary(null, body);
        diaryRepository.save(diary);
    }

    List<Diary> getDiaryList(){
        return diaryRepository.findAll();
    }

    public void delete(String id) {
        // 저장된 다이어리 리스트가 id 보다 클 경우
        long ID = Long.parseLong(id);
        diaryRepository.Delete(ID);
    }

    public void update(String id, String body) {
        long ID = Long.parseLong(id);
        final Diary diary = new Diary(ID, body);
        diaryRepository.update(diary);
    }
}
