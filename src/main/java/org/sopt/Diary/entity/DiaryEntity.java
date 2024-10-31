package org.sopt.Diary.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="diary")
public class DiaryEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    //Column의 기본값은 nullabel = true 이므로 기본 타입을 사용할 경우 nullable = false를 하는게 안전하다
    @Column(name="is_private", nullable = false)
    private boolean isPrivate;

    @Column(name="created_at")
    public LocalDateTime createdAt = LocalDateTime.now();

    @Column(name="category")
    @Enumerated(EnumType.STRING) //enum 이름 그대로 DB에 저장된다
    private Category category;

    @Column(name="user_id")
    private Long userId;

    //JPA 는 엔티티 객체를 생성할때 기본 생성자를 사용하므로  반드시 있어야 한다!
    public DiaryEntity() {}

    public DiaryEntity(final String title,final String content,final Category category,final boolean isPrivate,final long userId) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.isPrivate =isPrivate;
        this.userId = userId;
    }


    public DiaryEntity(final long id, final String title, final String content, final LocalDateTime createdAt, final Category category,final long userId) {
        this. id = id;
        this.title  = title;
        this.content = content;
        this.createdAt = createdAt;
        this.category = category;
        this.userId= userId;
    }

    public long getDiaryId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;}

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Category getCategory(){
        return category;
    }
    public long getUserId(){
        return userId;
    }

}

