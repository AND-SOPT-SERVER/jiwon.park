package org.sopt.Diary.service;

public class Diary {
    private final long id; //final 추가해서 불변하게 구현
    private final String name;
    public Diary(long id, String name){
        this.id = id;
        this.name = name;
    }

    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}
