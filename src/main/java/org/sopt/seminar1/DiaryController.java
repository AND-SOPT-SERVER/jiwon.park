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

        // APIS
        final List<Diary> getList() {
            return diaryService.getDiaryList();
        }

        final void post(final String body) {
            // 이런 경우 아랍아가 들어올 수 도 있고, 이모지가 들어올 때 어떻게 처리할지
            // 보통 바이트 수
            if(body.length()>30){
                throw new IllegalArgumentException();
            }
            diaryService.writeDiary(body);

        }

        final void delete(final String id) {
            diaryService.delete(id);
        }

        final void patch(final String id, final String body) {
            diaryService.update(id, body);
        }

        enum Status {
            READY,
            RUNNING,
            FINISHED,
            ERROR,
        }
    }

