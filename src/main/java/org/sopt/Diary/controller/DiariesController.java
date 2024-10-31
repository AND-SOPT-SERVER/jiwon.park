package org.sopt.Diary.controller;

import org.sopt.Diary.dto.res.DiariesResponse;
import org.sopt.Diary.entity.Category;
import org.sopt.Diary.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("diaries")
@RestController
public class DiariesController {

    private final DiaryService diaryService;

    public DiariesController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }


    @GetMapping("/diaries")
    public ResponseEntity<List<DiariesResponse>> getDiaries(
            @RequestParam(name = "category", required = false) Category category,
            @RequestParam(name = "sortByContentLength",required = false, defaultValue = "false")final Boolean sortByContentLength) {


        final List<DiariesResponse> diariesResponses;
        // 각 조건에 따라 한번만 할당되고 추가로 변경되지 않음을 명확하게 하기 위해 final 사용

        if (category != null) {
            // 카테고리로 정렬
            diariesResponses = diaryService.getDiaryListSortByCategory(category);
        } else if (sortByContentLength) {

            // 글자수로 정렬
            diariesResponses = diaryService.getDiaryListSortByContent();
        } else {
            //최신순으로 정렬
            diariesResponses = diaryService.getDiaryList();
        }

        return ResponseEntity.ok(diariesResponses);
    }
}
