package org.sopt.Diary.dto.req;


import org.sopt.Diary.entity.Category;

public record DiaryReq(String title,
                       String content,
                       Category category){
}
