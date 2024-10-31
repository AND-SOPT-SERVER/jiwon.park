package org.sopt.Diary.validator;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DiaryValidator {

    public static  String dateFormatter(LocalDateTime createdDate){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); //ForMat 을 하는 부분이
    return  createdDate.format(formatter);

    }
}