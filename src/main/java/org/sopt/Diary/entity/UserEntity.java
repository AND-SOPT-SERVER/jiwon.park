package org.sopt.Diary.entity;

import jakarta.persistence.*;

@Entity
@Table(name="user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="login_id")
    private String loginId;

    @Column(name="password")
    private String password;

    @Column(name="nickname")
    private String nickname;

    //기본 생성자
    public UserEntity(){};

}
