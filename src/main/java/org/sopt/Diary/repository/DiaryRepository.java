package org.sopt.Diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DiaryRepository extends JpaRepository<DiaryEntity,Long> {


    List<DiaryEntity> findTop10ByDelYnFalseOrderByDateDesc();

    DiaryEntity findByIdAndDelYnFalse(Long id);

}
