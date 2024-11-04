package org.sopt.Diary.dto.res;


import java.util.List;

public class DiaryListRes {
    private List<DiariesRes> diaries;

    public DiaryListRes(List<DiariesRes> diaries) {
        this.diaries = diaries;
    }

    public List<DiariesRes> getDiaries() {
        return diaries;
    }

    public void setDiaries(List<DiariesRes> diaries) {
        this.diaries = diaries;
    }
}