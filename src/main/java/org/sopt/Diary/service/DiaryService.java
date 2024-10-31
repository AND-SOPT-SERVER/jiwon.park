package org.sopt.Diary.service;

import org.sopt.Diary.dto.res.DiariesResponse;
import org.sopt.Diary.dto.req.DiaryReq;
import org.sopt.Diary.dto.res.DiaryResponse;
import org.sopt.Diary.entity.Category;
import org.sopt.Diary.entity.DiaryEntity;
import org.sopt.Diary.repository.DiaryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DiaryService {

    private final static int LIMIT_MINUTE = 5;
    private final static int LIMIT_DIARY = 10;
    private final DiaryRepository diaryRepository;
    private final UserService userService;

    public DiaryService(DiaryRepository diaryRepository, UserService userService) {
        this.diaryRepository = diaryRepository;
        this.userService = userService;
    }

    final public void createDiary(final long userId, final DiaryReq diaryRequest) {

       if(!userService.findById(userId)){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND);
       }

        validateTitle(diaryRequest.title());

        int minutesSinceLastDiary = calculateMinutesSinceLastDiary();
        if (minutesSinceLastDiary < LIMIT_MINUTE) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "5분 뒤에 요청해주세요");
        }

        diaryRepository.save(
                new DiaryEntity(diaryRequest.title(),
                diaryRequest.content(),
                diaryRequest.category(),
                diaryRequest.isPrivate())
        );
    }

    public DiaryResponse getDiary(long id) {
        DiaryEntity diaryEntity = findById(id);
        return new DiaryResponse(diaryEntity.getId(),
                diaryEntity.getTitle(),
                diaryEntity.getContent(),
                diaryEntity.getContent(),
                diaryEntity.getCategory());
    }


    private List<DiariesResponse> toDiariesResponse(List<DiaryEntity> diaryEntities) {
        List<DiariesResponse> diariesResponses = new ArrayList<>();
        int count = 0;

        for (DiaryEntity diary : diaryEntities) {
            if (count >= LIMIT_DIARY) break;
            DiariesResponse diariesResponse = new DiariesResponse(diary.getId(), diary.getTitle());
            diariesResponses.add(diariesResponse);
            count++;
        }
        return diariesResponses;
    }

    public List<DiariesResponse> getDiaryList() {
        List<DiaryEntity> diaryEntities = diaryRepository.findTop10ByOrderByCreatedAtDesc();
        return toDiariesResponse(diaryEntities);
    }

    public List<DiariesResponse> getDiaryListSortByContent() {
        List<DiaryEntity> diaryEntities = diaryRepository.findByContentLength();
        return toDiariesResponse(diaryEntities);
    }

    public List<DiariesResponse> getDiaryListSortByCategory(Category category) {
        List<DiaryEntity> diaryEntities = diaryRepository.findByCategory(category);
        return toDiariesResponse(diaryEntities);
    }



    public void patchDiary(Long id, String content, Category category) {
        DiaryEntity diaryEntity= findById(id);
        diaryRepository.save(new DiaryEntity(diaryEntity.getId(), diaryEntity.getTitle(), content, diaryEntity.getCreatedAt(), category));
    }

    public void deleteDiary(Long id) {
        DiaryEntity diaryEntity= findById(id);
        diaryRepository.delete(diaryEntity);
    }

    public DiaryEntity findById(final long id){
        return diaryRepository.findByIdAndIsPrivateFalse(id)
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
    }



    public void validateTitle(String title){
        if(diaryRepository.existsByTitle(title)){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"중복된 제목은 불가능 합니다.");
        }
    }

    private int calculateMinutesSinceLastDiary() {
        DiaryEntity latestDiary = diaryRepository.findTop1ByOrderByCreatedAtDesc();

        if (latestDiary != null) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime latest = latestDiary.getCreatedAt();
            Duration duration = Duration.between(latest, now);
            return duration.toMinutesPart();
        }
        return Integer.MAX_VALUE;  // 다이어리가 없을 경우, 제한 시간이 없도록 큰 값 반환
    }
}