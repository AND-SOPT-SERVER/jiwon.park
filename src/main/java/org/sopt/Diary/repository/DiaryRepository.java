package org.sopt.Diary.repository;

import org.sopt.Diary.service.Diary;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DiaryRepository extends JpaRepository<DiaryEntity,Long> {


    List<DiaryEntity> findTop10ByDelYnFalseOrderByDateDesc();

    DiaryEntity findByIdAndDelYnFalse(Long id);

    DiaryEntity findTop1ByDelYnFalseOrderByDateDesc();

    boolean existsByTitle(String title);

    @Query("SELECT d FROM DiaryEntity d WHERE d.delYn = false ORDER BY LENGTH(d.content) DESC")
    List<DiaryEntity> findTopByContentLengthAndDelYnFalse();

    List<DiaryEntity> findByCategoryAndDelYnFalse(Category category);
}
