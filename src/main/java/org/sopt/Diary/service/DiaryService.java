package org.sopt.Diary.service;

import jakarta.transaction.Transactional;
import org.sopt.Diary.dto.req.DiaryReq;
import org.sopt.Diary.dto.res.DiaryRes;
import org.sopt.Diary.entity.Category;
import org.sopt.Diary.entity.DiaryEntity;
import org.sopt.Diary.error.CustomException;
import org.sopt.Diary.error.ErrorCode;
import org.sopt.Diary.repository.DiaryRepository;
import org.springframework.stereotype.Service;


@Transactional
@Service
public class DiaryService {


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

    public DiaryRes getDiary(final Long userId, final long diaryId) {

        DiaryEntity diaryEntity = findByDiaryId(diaryId);

        //비공개 일기일 경우 = userId 검증
        if(diaryEntity.getIsPrivate()) {
            if( userId != diaryEntity.getUserId()){
                throw new CustomException(ErrorCode.ACCESS_DENIED);
            }
        }

        return new DiaryRes(diaryEntity.getDiaryId(),
                diaryEntity.getTitle(),
                diaryEntity.getContent(),
                diaryEntity.getContent(),
                diaryEntity.getCategory());
    }


    public void patchDiary(final long userId, final long diaryId, final String content, final Category category) {

        DiaryEntity diaryEntity = findByDiaryId(diaryId);

        if(diaryEntity.getUserId()!=userId){
            throw new CustomException(ErrorCode.ACCESS_DENIED);
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
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
        diaryRepository.delete(diaryEntity);
    }

    public DiaryEntity findByDiaryId(final long id){
        return diaryRepository.findById(id)
                .orElseThrow(() ->  new CustomException(ErrorCode.DIARY_NOT_FOUND));
    }


    public void validateTitle(final String title){
        if(diaryRepository.existsByTitle(title)){
            throw new CustomException(ErrorCode.DUPLICATE_TITLE);
        }
    }


}