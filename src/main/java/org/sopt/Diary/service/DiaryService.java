package org.sopt.Diary.service;

import org.sopt.Diary.dto.res.DiariesResponse;
import org.sopt.Diary.dto.req.DiaryRequest;
import org.sopt.Diary.repository.Category;
import org.sopt.Diary.repository.DiaryEntity;
import org.sopt.Diary.repository.DiaryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class DiaryService {

    private final static int LIMIT_MINUTE = 5;
    private final static int LIMIT_DIARY = 10;
    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public void validateTitle(String title){
        if(diaryRepository.existsByTitle(title)){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"중복왼 제목은 북자능 합니다.");
        }
    }

    public void createDiary(DiaryRequest diaryRequest) {
        validateTitle(diaryRequest.title());

        int minutesSinceLastDiary = calculateMinutesSinceLastDiary();
        if (minutesSinceLastDiary < LIMIT_MINUTE) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "5분 뒤에 요청해주세요");
        }

        Diary diary = new Diary(diaryRequest.title(), diaryRequest.content(), diaryRequest.category());
        diaryRepository.save(new DiaryEntity(diary.getTitle(), diary.getContent(), diary.getCategory()));
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

    public Diary getDiary(Long id) {
        DiaryEntity diaryEntity = findById(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = diaryEntity.getCreatedAt().format(formatter);

        return new Diary(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getContent(), formattedDate, diaryEntity.getCategory());
    }

    public void patchDiary(Long id, String content, Category category) {
        DiaryEntity diaryEntity= findById(id);
        diaryRepository.save(new DiaryEntity(diaryEntity.getId(), diaryEntity.getTitle(), content, diaryEntity.getCreatedAt(), category));
    }

    public void deleteDiary(Long id) {
        DiaryEntity diaryEntity= findById(id);
        diaryRepository.delete(diaryEntity);
    }

    public DiaryEntity findById(Long id){
        return diaryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}