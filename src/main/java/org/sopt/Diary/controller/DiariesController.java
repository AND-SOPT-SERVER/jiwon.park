package org.sopt.Diary.controller;

import jakarta.validation.Valid;
import org.sopt.Diary.dto.res.DiariesResponse;
import org.sopt.Diary.dto.res.DiaryListResponse;
import org.sopt.Diary.entity.Category;
import org.sopt.Diary.entity.SortType;
import org.sopt.Diary.service.DiariesService;
import org.sopt.Diary.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/diaries")
@RestController
public class DiariesController {

    private final DiariesService diariesService;

    public DiariesController(DiariesService diariesService) {
        this.diariesService = diariesService;
    }


    @GetMapping()
    public ResponseEntity<DiaryListResponse> getDiaries(
            @RequestParam(name = "category") final Category category,
            @RequestParam(name = "sort",required = false, defaultValue = "latest") final SortType sortType) {

        DiaryListResponse diaryListResponse = diariesService.getDiariesResponse(category, sortType, false, 0);
        return ResponseEntity.ok(diaryListResponse);
    }

    @GetMapping("/my")
    public ResponseEntity<DiaryListResponse> getMyDiaries(
            @RequestHeader("userId") long userId,
            @RequestParam(name = "category") final Category category,
            @RequestParam(name = "sort",required = false, defaultValue = "latest")final SortType sortType) {

        DiaryListResponse diaryListResponse = diariesService.getDiariesResponse(category, sortType, true, userId);
        return ResponseEntity.ok(diaryListResponse);
    }
}
