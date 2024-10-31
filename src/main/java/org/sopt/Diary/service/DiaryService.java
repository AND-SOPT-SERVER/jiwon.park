package org.sopt.Diary.service;

import org.sopt.Diary.dto.req.DiaryReq;
import org.sopt.Diary.dto.res.DiaryResponse;
import org.sopt.Diary.entity.Category;
import org.sopt.Diary.entity.DiaryEntity;
import org.sopt.Diary.repository.DiaryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;


@Component
public class DiaryService {

    private final static int LIMIT_MINUTE = 5;

    private final DiaryRepository diaryRepository;


    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;

    }

    final public void createDiary(final long userId, final DiaryReq diaryRequest) {

        validateTitle(diaryRequest.title());
        diaryRepository.save(
                new DiaryEntity(diaryRequest.title(),
                diaryRequest.content(),
                diaryRequest.category(),
                diaryRequest.isPrivate(),
                userId)
        );
    }

    public DiaryResponse getDiary(final long id) {

        DiaryEntity diaryEntity = findByDiaryIdPrivateFalse(id);

        return new DiaryResponse(diaryEntity.getDiaryId(),
                diaryEntity.getTitle(),
                diaryEntity.getContent(),
                diaryEntity.getContent(),
                diaryEntity.getCategory());
    }

    public void patchDiary(final long userId, final long diaryId, final String content, final Category category) {

        DiaryEntity diaryEntity = findByDiaryId(diaryId);

        if(diaryEntity.getUserId()!=userId){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        diaryRepository.save(new DiaryEntity(diaryEntity.getDiaryId(),
                diaryEntity.getTitle(),
                content,
                diaryEntity.getCreatedAt(),
                category,
                diaryEntity.getUserId()));
    }

    public void deleteDiary(final long userId, final long diaryId) {

        DiaryEntity diaryEntity= findByDiaryId(diaryId);

        if(diaryEntity.getUserId()!=userId){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        diaryRepository.delete(diaryEntity);
    }

    public DiaryEntity findByDiaryId(final long id){
        return diaryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"일기를 찾을 수 없습니다"));
    }

    public DiaryEntity findByDiaryIdPrivateFalse(final long id){
        return diaryRepository.findByIdAndIsPrivateFalse(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"일기를 찾을 수 없습니다"));
    }

    public void validateTitle(final String title){
        if(diaryRepository.existsByTitle(title)){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"중복된 제목은 불가능 합니다.");
        }
    }

}