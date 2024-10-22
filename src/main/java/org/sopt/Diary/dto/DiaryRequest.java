package org.sopt.Diary.dto;


import org.sopt.Diary.repository.DiaryEntity;

public class DiaryRequest {
    private String title;
    private String content;

    public DiaryRequest(){

    }

    public DiaryRequest(String title, String content){
        this.title= title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
