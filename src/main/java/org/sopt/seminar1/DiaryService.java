package org.sopt.seminar1;

import java.io.IOException;
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

     void timer(){

         Calendar calendar = Calendar.getInstance();
         calendar.set(Calendar.HOUR_OF_DAY, 0);
         calendar.set(Calendar.MINUTE, 0);
         calendar.set(Calendar.SECOND, 0);
         calendar.set(Calendar.MILLISECOND, 0);
         calendar.add(Calendar.DAY_OF_YEAR, 1); // 다음 날

         Date next = calendar.getTime(); // 다음날 자정

        long delay = next.getTime()- System.currentTimeMillis(); //지금부터 다음날 자정까지의 시간 게산
        long period = 86400000; // 24시간 (24시간 * 60분 * 60초 * 1000밀리초)

        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                updateAllDiaries();
                System.out.println("초기화되었습니다");
            }
        };
        t.scheduleAtFixedRate(task, delay, period);

    }

    private void updateAllDiaries() {
        List<Diary> diaryListAll = diaryRepository.getDiaryList();
        for (Diary diary : diaryListAll) {
            diary.setUpdateCount(0); // 모든 업데이트 카운트 초기화
        }
        diaryRepository.saveDiaryList(diaryListAll);
    }


}
