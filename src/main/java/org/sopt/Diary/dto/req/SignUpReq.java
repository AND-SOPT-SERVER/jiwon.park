package org.sopt.Diary.dto.req;

import jakarta.validation.constraints.NotNull;

//입력값의 Null 검증을 위한 @Valid, @NotNull
public record SignUpReq ( @NotNull String loginId, @NotNull String password, @NotNull String nickname )
{

}