package org.sopt.Diary.service;

import jakarta.transaction.Transactional;
import org.sopt.Diary.formatter.DiaryFormatter;
import org.sopt.Diary.dto.res.DiariesRes;
import org.sopt.Diary.dto.res.DiaryListRes;
import org.sopt.Diary.entity.Category;
import org.sopt.Diary.entity.DiaryEntity;
import org.sopt.Diary.entity.SortType;
import org.sopt.Diary.entity.UserEntity;
import org.sopt.Diary.repository.DiaryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class DiariesService {

    private final static int LIMIT_DIARY = 10;
    private final DiaryRepository diaryRepository;
    private final UserService userService;

    public DiariesService( DiaryRepository diaryRepository,  UserService userService) {
        this.diaryRepository = diaryRepository;
        this.userService = userService;
    }

    private List<DiariesRes> getDiaryResponse(List<DiaryEntity> diaryEntities) {
        List<DiariesRes> diariesResponse = new ArrayList<>();
        int count =0;
        for (DiaryEntity diary : diaryEntities) {
            if(count < LIMIT_DIARY) {
                final UserEntity user = userService.findByUserId(diary.getUserId());
                final String createdAt = DiaryFormatter.dateFormatter(diary.getCreatedAt());
                DiariesRes diariesRes = new DiariesRes(diary.getDiaryId(), user.getNickname(), diary.getTitle(), createdAt);
                diariesResponse.add(diariesRes);
                count++;
            }
        }
        return diariesResponse;
    }

    public List<DiariesRes> getDiaryList(Category category, SortType sortType) {
        List<DiaryEntity> diaryEntities;

        if (category == Category.all) {
            diaryEntities = switch (sortType) {
                case latest -> diaryRepository.findAllByIsPrivateFalseOrderByCreatedAtDesc()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일기를 찾을 수 없습니다"));
                case quantity -> diaryRepository.findAllByIsPrivateFalseOrderByContentLengthDesc()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일기를 찾을 수 없습니다"));
            };
        } else {
            diaryEntities = switch (sortType) {
                case latest -> diaryRepository.findByCategoryAndIsPrivateFalseOrderByCreatedAtDesc(category)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일기를 찾을 수 없습니다"));
                case quantity -> diaryRepository.findByCategoryAndIsPrivateFalseOrderByContentLengthDesc(category)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일기를 찾을 수 없습니다"));
            };
        }
        return getDiaryResponse(diaryEntities);
    }

    public List<DiariesRes> getMyDiaryList(long userId, Category category, SortType sortType) {
        List<DiaryEntity> diaryEntities;

        if (category == Category.all) {
            diaryEntities = switch (sortType) {
                case latest -> diaryRepository.findByUserIdOrderByCreatedAtDesc(userId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일기를 찾을 수 없습니다"));
                case quantity -> diaryRepository.findByUserIdOrderByContentLengthDesc(userId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일기를 찾을 수 없습니다"));

            };
        } else {
            diaryEntities = switch (sortType) {
                case latest -> diaryRepository.findByUserIdAndCategoryOrderByCreatedAtDesc(userId, category)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일기를 찾을 수 없습니다"));
                case quantity -> diaryRepository.findByUserIdAndCategoryOrderByContentLengthDesc(userId, category)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일기를 찾을 수 없습니다"));
            };
        }

        return getDiaryResponse(diaryEntities);
    }

    public DiaryListRes getDiariesResponse(Category category, SortType sortType, boolean isMine, long userId) {
        List<DiariesRes> diaryResponses;

        if (isMine) {
            diaryResponses = getMyDiaryList(userId, category, sortType);
        } else {
            diaryResponses = getDiaryList(category, sortType);
        }

        return new DiaryListRes(diaryResponses);
    }

}

