package org.sopt.Diary.service;

import org.sopt.Diary.repository.Category;

import java.time.LocalDateTime;

public class Diary {
    private  final Long id; //final 추가해서 불변하게 구현
    private  String title;
    private  String content;
    private String date;
    private Category category;

    public Diary(long id, String title, String content, String date,Category category){
        this.id = id;
        this.title = title;
        this.content = content;
        this.date= date;
        this.category = category;
    }


    public Diary(String title, String content, LocalDateTime now,Category category){
        this.id = null;
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

    public String getDate() {
        return date;
    }
    public Category getCategory(){
        return category;
    }
}
