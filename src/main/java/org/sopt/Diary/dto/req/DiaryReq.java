package org.sopt.Diary.dto.req;


import jakarta.validation.constraints.NotNull;
import org.sopt.Diary.entity.Category;


public record DiaryReq(@NotNull String title,
                       @NotNull String content,
                       @NotNull Category category,
                       boolean isPrivate){
}
