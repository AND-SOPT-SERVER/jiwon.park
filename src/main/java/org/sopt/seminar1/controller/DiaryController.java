package org.sopt.seminar1.controller;

import org.sopt.seminar1.entity.Diary;
import org.sopt.seminar1.service.DiaryService;
import org.sopt.seminar1.repository.DiaryRepository;
import org.sopt.seminar1.validator.DiaryValidator;

import java.util.List;

public class DiaryController {

    private Status status = Status.READY;
    private final DiaryService diaryService = new DiaryService();
    private  final DiaryValidator diaryValidator =new DiaryValidator();
    private final DiaryRepository diaryRepository = new DiaryRepository();

    public final Status getStatus() {
        return status;
    }

    public final void boot() {
        this.status = Status.RUNNING;
    }

    public final void finish() {
        this.status = Status.FINISHED;
         saveChanges();
    }

    public final void saveChanges(){
        diaryRepository.saveChangesToFile();
    }

    // APIS
    public final List<Diary> getList() {
        return diaryService.getDiaryList();
    }

    public final void post(final String body) {
        // 보통 바이트 수
        if(diaryValidator.validation(body)) {
            diaryService.writeDiary(body);
        }
    }

    public final void delete(final String id) {
        diaryService.deleteDiary(Long.parseLong(id));
    }

    public final void patch(final String id, final String body) {
        if (diaryValidator.validation(body)) {
            long ID = Long.parseLong(id);
            Diary diary = new Diary(ID, body);
            diaryService.update(diary);
        }
    }

    public final void restore(final String id) {
         diaryService.restore(Long.parseLong(id));

    }

    public enum Status {
            READY,
            RUNNING,
            FINISHED,
            ERROR,
        }
    }

