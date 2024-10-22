package org.sopt.Diary.api;

import org.sopt.Diary.dto.DiariesResponse;
import org.sopt.Diary.dto.DiaryRequest;
import org.sopt.Diary.service.Diary;
import org.sopt.Diary.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class DiaryController {

    private final DiaryService diaryService;
    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }
    private final static int LengthLimit = 30;

    @PostMapping("/diary")
    ResponseEntity<String> postDiary(@RequestBody DiaryRequest diaryRequest) {

        if(diaryRequest.getContent().length()>LengthLimit){
            return ResponseEntity.badRequest().body("글자 수는 30자를 넘을 수 없습니다");
        }
        diaryService.createDiary(diaryRequest);
        return ResponseEntity.ok("일기가 생성되었습니다.");
    }

    @GetMapping("/diaries")
    public ResponseEntity<DiaryListResponse> getDiaries() {
        List<DiariesResponse> diaries = diaryService.getDiaryList();
        return ResponseEntity.ok(new DiaryListResponse(diaries));
    }

    @GetMapping("/diary/{id}")
    ResponseEntity<DiaryResponse> getDiary(@PathVariable Long id) {
        Diary savedDiary = diaryService.getDiary(id);

        DiaryResponse diaryResponse = new DiaryResponse(
                savedDiary.getId(),
                savedDiary.getTitle(),
                savedDiary.getContent(),
                savedDiary.getDate()
        );
        return ResponseEntity.ok(diaryResponse);
    }

    @PatchMapping("/diary/{id}")
    ResponseEntity<String> updateDiary(@PathVariable Long id, @RequestBody DiaryRequest diaryRequest){
        if (diaryRequest.getContent().length() > LengthLimit) {
            return ResponseEntity.badRequest().body("글자 수는 30자를 넘을 수 없습니다");
        }
        diaryService.patchDiary(id,diaryRequest.getContent());
        return ResponseEntity.ok("일기가 수정되었습니다.");
    }

    @DeleteMapping("diary/{id}")
    ResponseEntity<String> deleteDiary(@PathVariable Long id){
        diaryService.deleteDiary(id);
        return ResponseEntity.ok("일기가 삭제되었습니다.");
    }
}
