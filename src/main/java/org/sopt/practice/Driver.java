package org.sopt.practice;

public class Driver {
    private Person person;
    Driver(Person person){
        this.person= person;
    }
    boolean canDrive(){
        return  person.getAge1()>20;
        //MPE - Null Point Exception: 동작하는 중에 발생함

    }
}
