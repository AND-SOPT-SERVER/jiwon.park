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

    /**
     * 일기 작성하기
     * @param userId 유저 Id(Header)
     * @param diaryRequest title,content,category,isPrivate
     * @return 200
     */
    @PostMapping()
    ResponseEntity<String> postDiary(@RequestHeader("userId") long userId, @Valid @RequestBody final DiaryReq diaryRequest) {

        if(diaryRequest.content().length()>LENGTH_LIMIT){
            return ResponseEntity.badRequest().body("글자 수는 30자를 넘을 수 없습니다");
        }

        diaryService.createDiary(userId, diaryRequest);
        return ResponseEntity.ok("일기가 생성되었습니다.");
    }


    /**
     * 일기 상세 조회
     * @param diaryId 다이어리 아이디
     * @return 200
     */
    @GetMapping("/{diaryId}")
    ResponseEntity<DiaryResponse> getDiary( @PathVariable("diaryId") final long diaryId) {
        final DiaryResponse diaryResponse = diaryService.getDiary(diaryId);
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
