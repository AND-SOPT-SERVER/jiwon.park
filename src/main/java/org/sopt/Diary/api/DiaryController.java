package org.sopt.Diary.api;

import org.sopt.Diary.service.Diary;
import org.sopt.Diary.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/post")
    void post() {
        diaryService.createDiary();
    }



    @GetMapping("/post")
    ResponseEntity<DiaryListResponse> get() {
        // Service로부터 가져온 DiaryList
        List<Diary> diaryList = diaryService.getList();

        // Client 와 협의한 interface 로 변화
        List<DiaryResponse> diaryResponseList = new ArrayList<>();
        for(Diary diary : diaryList) {
            diaryResponseList.add(new DiaryResponse(diary.getId(), diary.getName()));
        }

        return ResponseEntity.ok(new DiaryListResponse(diaryResponseList));
    }
}
