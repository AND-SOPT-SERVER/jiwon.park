package org.sopt.Diary.api;

import org.sopt.Diary.dto.req.DiaryUpdateRequest;
import org.sopt.Diary.dto.res.DiariesResponse;
import org.sopt.Diary.dto.req.DiaryRequest;
import org.sopt.Diary.dto.res.DiaryResponse;
import org.sopt.Diary.repository.Category;
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

    private final static int LENGTH_LIMIT = 30;

    @PostMapping("/diary")
    ResponseEntity<String> postDiary(@RequestBody final DiaryRequest  diaryRequest) {
        //final 을 붙여서 매개변수의 재할당을 막음!!

        if(diaryRequest.content().length()>LENGTH_LIMIT){
            return ResponseEntity.badRequest().body("글자 수는 30자를 넘을 수 없습니다");
        }
        diaryService.createDiary(diaryRequest);
        return ResponseEntity.ok("일기가 생성되었습니다.");
    }

    @GetMapping("/diaries")
    public ResponseEntity<List<DiariesResponse>> getDiaries(
            @RequestParam(name = "category", required = false)  Category category,
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
    ResponseEntity<String> updateDiary(@PathVariable(name = "id") final Long id, @RequestBody DiaryUpdateRequest diaryRequest){

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
