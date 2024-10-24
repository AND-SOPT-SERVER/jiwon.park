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
    public LocalDateTime date = LocalDateTime.now();

    @Column(columnDefinition = "boolean default false")
    private Boolean delYn = false;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    // 생성자
    public DiaryEntity(final Long id, final String title, final String content, final LocalDateTime date, final Category category) {
        this. id = id;
        this.title  = title;
        this.content = content;
        this.date = date;
        this.delYn = false;
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

    public LocalDateTime getDate(){
        return date;}

    public Boolean getDelYn() {
        return delYn;
    }

    public Category getCategory(){
        return category;
    }

    // 삭제 처리 메서드
    public void delete() {
        this.delYn = true; // 삭제 상태로 변경
    }
}
