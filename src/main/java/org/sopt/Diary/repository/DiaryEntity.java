package org.sopt.Diary.repository;

import jakarta.persistence.*;

@Entity
public class DiaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    public String name;

    public DiaryEntity(final String name) {
        this.name = name;
    }

    public DiaryEntity() {

    }


    public long getId(){
            return id;
    }

    public String getName(){
        return name;
    }

    public DiaryEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
