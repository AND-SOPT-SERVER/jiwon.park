package org.sopt.Diary.entity;

import jakarta.persistence.*;

@Entity
@Table(name="user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="login_id")
    private String loginId;

    @Column(name="password")
    private String password;

    @Column(name="nickname")
    private String nickname;

    //기본 생성자
    public UserEntity(){};

    public UserEntity(final String loginId,final String password, final String nickname){
        this.loginId =loginId;
        this.password=password;
        this.nickname=nickname;
    }

    public static UserEntity createUser(final String loginId,final String password, final String nickname){
        return new UserEntity(loginId, password, nickname);
    }

    public long getId(){
        return this.id;
    }
    public String getNickname(){
        return this.nickname;
    }



}
