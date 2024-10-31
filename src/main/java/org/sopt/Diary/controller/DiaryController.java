package org.sopt.Diary.controller;

import jakarta.validation.Valid;
import org.sopt.Diary.dto.req.DiaryUpdateReq;
import org.sopt.Diary.dto.res.DiariesResponse;
import org.sopt.Diary.dto.req.DiaryReq;
import org.sopt.Diary.dto.res.DiaryResponse;
import org.sopt.Diary.entity.Category;
import org.sopt.Diary.dto.Diary;
import org.sopt.Diary.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequestMapping("/diary")
@RestController
public class DiaryController {

    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    private final static int LENGTH_LIMIT = 30;

    @PostMapping()
    ResponseEntity<String> postDiary(@RequestHeader("userId") Long userId, @Valid @RequestBody final DiaryReq diaryRequest) {

        if(diaryRequest.content().length()>LENGTH_LIMIT){
            return ResponseEntity.badRequest().body("글자 수는 30자를 넘을 수 없습니다");
        }

        diaryService.createDiary(userId, diaryRequest);
        return ResponseEntity.ok("일기가 생성되었습니다.");
    }


    @GetMapping("/diary/{id}")
    ResponseEntity<DiaryResponse> getDiary(@PathVariable(name = "id") final Long id) {
        final Diary savedDiary = diaryService.getDiary(id);

        // new 가 아닌 factory 메소드를 사용하면 불변 객체를 만들 수 있는데... 이 부분에 대해서 고민중..
        final DiaryResponse diaryResponse = new DiaryResponse(
                savedDiary.getId(),
                savedDiary.getTitle(),
                savedDiary.getContent(),
                savedDiary.getCreatedAt(),
                savedDiary.getCategory()
        );

        return ResponseEntity.ok(diaryResponse);
    }

    @PatchMapping("/diary/{id}")
    ResponseEntity<String> updateDiary(@PathVariable(name = "id") final Long id, @RequestBody DiaryUpdateReq diaryRequest){

        if (diaryRequest.content().length() > LENGTH_LIMIT) {
            return ResponseEntity.badRequest().body("글자 수는 30자를 넘을 수 없습니다");
        }
        diaryService.patchDiary(id,diaryRequest.content(),diaryRequest.category());
        return ResponseEntity.ok("일기가 수정되었습니다.");
    }

    @DeleteMapping("/diary/{id}")
    ResponseEntity<String> deleteDiary(@PathVariable(name = "id") final Long id){
        diaryService.deleteDiary(id);
        return ResponseEntity.ok("일기가 삭제되었습니다.");
    }
}
