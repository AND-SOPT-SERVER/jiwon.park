package org.sopt.seminar1;

import java.util.ArrayList;
import java.util.List;

public class DiaryController {

        private Status status = Status.READY;
        private final DiaryService diaryService = new DiaryService();

        Status getStatus() {
            return status;
        }

        void boot() {
            this.status = Status.RUNNING;
        }

        void finish() {
            this.status = Status.FINISHED;
        }

        private final int MAX_LENGTH=30;

        // APIS
        final List<Diary> getList() {
            return diaryService.getDiaryList();
        }

        final void post(final String body) {
            // 이런 경우 아랍아가 들어올 수 도 있고, 이모지가 들어올 때 어떻게 처리할지
            // 보통 바이트 수
            if(validation(body)) {
                diaryService.writeDiary(body);
            }
        }

        final void delete(final String id) {
            diaryService.delete(id);
        }
        final void patch(final String id, final String body) {
            if(validation(body)) {
                diaryService.update(id, body);
            }
        }
     void restore(final String id) {
         diaryService.restore(id);

    }
    boolean validation(final String body){

            if(body.length() > MAX_LENGTH ||body.trim().isBlank() ){
                System.out.println("1~30 글자수를 맞춰주세요");
                return false;
            }
         return true;
    }

    enum Status {
            READY,
            RUNNING,
            FINISHED,
            ERROR,
        }
    }

