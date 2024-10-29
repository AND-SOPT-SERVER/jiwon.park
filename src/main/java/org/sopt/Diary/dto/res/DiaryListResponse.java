package org.sopt.Diary.dto.res;

import java.util.List;

public record DiaryListResponse(List<DiariesResponse> diaries) { }
// 레코드 생성자 변경