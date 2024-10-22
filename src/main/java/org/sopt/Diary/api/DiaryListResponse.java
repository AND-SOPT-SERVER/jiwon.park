package org.sopt.Diary.api;

import org.sopt.Diary.dto.DiariesResponse;

import java.util.List;

public class DiaryListResponse {
    private List<DiariesResponse> diaries; // 필드 이름을 diaries로 변경

    public DiaryListResponse(List<DiariesResponse> diaries) {
        this.diaries = diaries;
    }

    public List<DiariesResponse> getDiaries() {
        return diaries;
    }
}
