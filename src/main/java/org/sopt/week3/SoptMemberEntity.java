package org.sopt.week3;

import jakarta.persistence.*;

@Entity //Repository 가 스캔하면서 이 어노테이션이 붙은 클래스를 DB의 테이블과 매핑시켜줌
@Table(name="sopt_member") // 어떤 테이블을 매핑시켜줄지
public class SoptMemberEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name",nullable= false) //여기서 true, DB false 로 했을 경우
    private String name;

    @Column(name="age",nullable=false)
    private int age;

    public SoptMemberEntity(){}

    public SoptMemberEntity(String name, int age){
        this.name=name;
        this.age =age;
    }

    @Override
    public String toString(){
        return this.name;
    }

}
