package org.sopt.Diary.dto;


import org.sopt.Diary.repository.Category;

public class DiaryRequest {
    private String title;
    private String content;
    private Category category;

    public DiaryRequest(){

    }

    public DiaryRequest(String title, String content,Category category){
        this.title= title;
        this.content = content;
        this.category= category;
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
    public Category getCategory(){
        return category;
    }
}
