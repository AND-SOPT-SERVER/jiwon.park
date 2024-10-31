package org.sopt.Diary.dto;

import org.sopt.Diary.entity.Category;

public class Diary {
    private  long id;
    private  final String title;
    private  final String content;
    private  String createdAt;
    private  Category category;

    public Diary(long id, String title, String content, String date,Category category){
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt= date;
        this.category = category;
    }


    public Diary(String title, String content,Category category){
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public Diary(long id, String content){
        this.id= id;
        this.title = null;
        this.content = content;
    }

    public long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Category getCategory(){
        return category;
    }
}
