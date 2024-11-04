package org.sopt.Diary.repository;

import org.sopt.Diary.entity.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {

    Boolean existsByTitle(String title);

    List<DiaryEntity> findAllByIsPrivateFalse();

    // 필수적으로 UserId 필요
    List<DiaryEntity> findByUserId(long userId);

    List<DiaryEntity> findByUserIdOrIsPrivateFalse(Long userId);
}
