package org.sopt.Diary.controller;

import org.sopt.Diary.dto.res.DiaryListRes;
import org.sopt.Diary.entity.Category;
import org.sopt.Diary.entity.SortType;
import org.sopt.Diary.service.DiariesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/diaries")
@RestController
public class DiariesController {

    private final DiariesService diariesService;

    public DiariesController(DiariesService diariesService) {
        this.diariesService = diariesService;
    }

    @GetMapping()
    public ResponseEntity<DiaryListRes> getDiaries(
            @RequestHeader(name="userId" , required = false) Long userId,
            @RequestParam(name = "category" , required = false, defaultValue = "all") final Category category,
            @RequestParam(name = "sort",required = false, defaultValue = "latest") final SortType sortType) {

        DiaryListRes diaryListRes = diariesService.getDiariesResponse( category,sortType, false, userId);
        return ResponseEntity.ok(diaryListRes);
    }

    @GetMapping("/my")
    public ResponseEntity<DiaryListRes> getMyDiaries(
            @RequestHeader("userId") long userId,
            @RequestParam(name = "category",required = false, defaultValue = "all") final Category category,
            @RequestParam(name = "sort",required = false, defaultValue = "latest")final SortType sortType) {

        DiaryListRes diaryListRes = diariesService.getDiariesResponse(category, sortType,true,userId);
        return ResponseEntity.ok(diaryListRes);
    }
}
