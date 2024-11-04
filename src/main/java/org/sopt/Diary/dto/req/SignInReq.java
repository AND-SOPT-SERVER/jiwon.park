package org.sopt.Diary.dto.req;

import jakarta.validation.constraints.NotNull;

public record SignInReq (@NotNull String loginId, @NotNull String password){
}
