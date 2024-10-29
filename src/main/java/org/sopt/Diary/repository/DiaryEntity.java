package org.sopt.Diary.repository;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class DiaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    public String title;

    @Column
    public String content;

    @Column
    public LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Category category;

    public DiaryEntity(final Long id, final String title, final String content, final LocalDateTime createdAt, final Category category) {
        this. id = id;
        this.title  = title;
        this.content = content;
        this.createdAt = createdAt;
        this.category = category;
    }

    public DiaryEntity() {

    }

    public DiaryEntity(String title, String content,Category category) {
        this.title = title;
        this.content = content;
        this.category = category;

    }

    public long getId(){
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

}
