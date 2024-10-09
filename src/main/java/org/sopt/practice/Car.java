package org.sopt.practice;

public class Car implements Vehicle{
    private final String  name;
    private final String manufacturer;
    private int fuel;

    //생성자
    public Car(String name, String manu) {
        this.name = name;
        this.manufacturer = manu;
    }

    public void bbang(){
        System.out.println("빵!!!!");
    }
  public String run(Driver driver){
        if(driver.canDrive()){
            return "자동차 시동겁니다";

        }
        else{
            return "안됩니다";
        }
  }


}
