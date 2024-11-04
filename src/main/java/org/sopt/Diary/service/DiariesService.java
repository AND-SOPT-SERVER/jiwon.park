package org.sopt.Diary.service;

import org.sopt.Diary.dto.res.DiaryListRes;
import org.sopt.Diary.formatter.DiaryFormatter;
import org.sopt.Diary.dto.res.DiariesRes;
import org.sopt.Diary.entity.Category;
import org.sopt.Diary.entity.DiaryEntity;
import org.sopt.Diary.entity.SortType;
import org.sopt.Diary.entity.UserEntity;
import org.sopt.Diary.repository.DiaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly=true)
@Service
public class DiariesService {

    private final static int LIMIT_DIARY = 10;
    private final DiaryRepository diaryRepository;
    private final UserService userService;

    public DiariesService(DiaryRepository diaryRepository, UserService userService) {
        this.diaryRepository = diaryRepository;
        this.userService = userService;
    }


    public DiaryListRes getDiariesResponse(Category category, SortType sortType, boolean isMine, Long userId) {
        List<DiaryEntity> diaryEntities;

        if (isMine) {
            //  /my 로 들어온 경우 - 내가 쓴 일기만 조회
            diaryEntities = diaryRepository.findByUserId(userId);
        } else if (userId == null) {
            // userId 없는 경우 - 공개된 일기만 조회
            diaryEntities = diaryRepository.findAllByIsPrivateFalse();
        } else {
            // userId가 null 이 아닌 경우, 해당 user 의 일기와 isPrivateFalse 인 일기가 보이도록
            diaryEntities = diaryRepository.findByUserIdOrIsPrivateFalse(userId);
        }

        // 선택된 카테고리에 따라 필터링
        if (category != Category.ALL) {
            diaryEntities = diaryEntities.stream()
                    .filter(diary -> diary.getCategory() == category)
                    .collect(Collectors.toList());
        }

        // sortType 에 따른 필터링
        Comparator<DiaryEntity> comparator = getComparator(sortType);
        diaryEntities = diaryEntities.stream()
                .sorted(comparator)
                .limit(LIMIT_DIARY)  // 10개 제한
                .collect(Collectors.toList());

        return new DiaryListRes(getDiaryResponse(diaryEntities));
    }

    private Comparator<DiaryEntity> getComparator(SortType sortType) {
        return switch (sortType) {
            case LATEST -> Comparator.comparing(DiaryEntity::getCreatedAt).reversed(); // 최신 순 정렬
            case QUANTITY -> Comparator.comparingInt((DiaryEntity diary) -> diary.getContent().length()).reversed(); // 내용 길이 기준 내림차순 정렬
        };
    }

    // userNickName, date 형식 변환을 위한 메소드
    private List<DiariesRes> getDiaryResponse(List<DiaryEntity> diaryEntities) {
        return diaryEntities.stream()
                .map(diary -> {
                    final UserEntity user = userService.findByUserId(diary.getUserId());
                    final String createdAt = DiaryFormatter.dateFormatter(diary.getCreatedAt());
                    return new DiariesRes(diary.getDiaryId(), user.getNickname(), diary.getTitle(), createdAt);
                })
                .collect(Collectors.toList());
    }

}

