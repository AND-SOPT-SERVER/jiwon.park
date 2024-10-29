package org.sopt.Diary.dto.req;


import org.sopt.Diary.repository.Category;

public record DiaryUpdateRequest( String content , Category category) {
}
