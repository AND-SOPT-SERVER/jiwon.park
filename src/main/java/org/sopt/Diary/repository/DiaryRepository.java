package org.sopt.Diary.repository;

import org.sopt.Diary.entity.Category;
import org.sopt.Diary.entity.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DiaryRepository extends JpaRepository<DiaryEntity,Long> {


    List<DiaryEntity> findTop10ByOrderByCreatedAtDesc();

    DiaryEntity findTop1ByOrderByCreatedAtDesc();

    Boolean existsByTitle(String title);

    @Query("SELECT d FROM DiaryEntity d ORDER BY LENGTH(d.content) DESC")
    List<DiaryEntity> findByContentLength();

    List<DiaryEntity> findByCategory(Category category);
}
