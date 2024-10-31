package org.sopt.Diary.dto.res;

public class UserRes {

    private long userId;

    public UserRes(long userId){
        this.userId = userId;
    }

    public long getUserId(){
        return  userId;
    }

}

