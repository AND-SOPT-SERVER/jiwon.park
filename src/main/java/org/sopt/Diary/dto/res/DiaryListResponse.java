package org.sopt.Diary.dto.res;


import java.util.List;

public class DiaryListResponse {
    private List<DiariesResponse> diaries;

    public DiaryListResponse(List<DiariesResponse> diaries) {
        this.diaries = diaries;
    }

    public List<DiariesResponse> getDiaries() {
        return diaries;
    }

    public void setDiaries(List<DiariesResponse> diaries) {
        this.diaries = diaries;
    }
}