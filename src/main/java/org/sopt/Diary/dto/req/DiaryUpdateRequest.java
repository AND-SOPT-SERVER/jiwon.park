package org.sopt.Diary.dto.req;


import org.sopt.Diary.repository.Category;

public class DiaryUpdateRequest {
    private String content;
    private Category category;

    public DiaryUpdateRequest(){

    }

    public DiaryUpdateRequest( String content, Category category){

        this.content = content;
        this.category= category;
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
