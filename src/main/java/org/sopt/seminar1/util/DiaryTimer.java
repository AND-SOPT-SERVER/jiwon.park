package org.sopt.seminar1.util;

import org.sopt.seminar1.service.DiaryService;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class DiaryTimer {

    private final static long PERIOD_TIME = 86400000; //24 시간
    private final DiaryService diaryService = new DiaryService();

    public void timer(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_YEAR, 1); // 다음 날

        Date next = calendar.getTime(); // 다음날 자정

        long delay = next.getTime()- System.currentTimeMillis(); //지금부터 다음날 자정까지의 시간 게산

        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                diaryService.updateAllDiaries();
                System.out.println("초기화되었습니다");
            }
        };
        t.scheduleAtFixedRate(task, delay, PERIOD_TIME);

    }

}
