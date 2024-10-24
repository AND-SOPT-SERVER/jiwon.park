package org.sopt.Diary.dto;

import org.sopt.Diary.repository.Category;

public class DiariesResponse {

    private Long id;
    private String title;


    public DiariesResponse(long id, String title){
        this.id = id;
        this.title = title;

    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
