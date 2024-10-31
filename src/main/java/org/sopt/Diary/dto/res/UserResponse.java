package org.sopt.Diary.dto.res;

public class UserResponse {

    private long userId;

    public UserResponse(long userId){
        this.userId = userId;
    }

    public long getUserId(){
        return  userId;
    }

}

