package org.sopt.Diary.dto.res;

import org.sopt.Diary.entity.Category;

public record DiaryRes(long id, String title, String content, String createdAt, Category category) {
}