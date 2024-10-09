package org.sopt.practice;

public class Person {
    private final int age;
    Person(int age){
        this.age =age;
    }
    int getAge(){
       // return null; //primitive 는 null 이 될 수 없다
        return this.age;
    }
    Integer getAge1(){
        return null;
    }
}
