package org.sopt.Diary.dto.req;


import org.sopt.Diary.entity.Category;

public record DiaryUpdateReq(String content , Category category) {
}
