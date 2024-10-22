package org.sopt.Diary.api;

import org.sopt.Diary.repository.DiaryRepository;

import java.util.List;

public class DiaryListResponse {
    private List<DiaryResponse> diaryList;

    public DiaryListResponse(List<DiaryResponse> diaryList) {
        this.diaryList = diaryList;
    }

    public List<DiaryResponse> getDiaryList() {
        return diaryList;
    }
}

