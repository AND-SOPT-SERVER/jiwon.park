package org.sopt.Diary.dto.res;

import org.sopt.Diary.repository.Category;

public record DiaryResponse(long id, String title, String content, String createdAt, Category category) {
}