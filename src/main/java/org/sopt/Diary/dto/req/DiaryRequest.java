package org.sopt.Diary.dto.req;


import org.sopt.Diary.repository.Category;

public record DiaryRequest ( String title,
                             String content,
                             Category category){
}
