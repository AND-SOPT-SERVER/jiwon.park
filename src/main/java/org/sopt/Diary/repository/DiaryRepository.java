package org.sopt.Diary.repository;

import org.sopt.Diary.entity.Category;
import org.sopt.Diary.entity.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface DiaryRepository extends JpaRepository<DiaryEntity,Long> {


    Boolean existsByTitle(String title);

    Optional<DiaryEntity> findByIdAndIsPrivateFalse(Long id);

    //카테고리 필터링
    Optional<List<DiaryEntity>> findByCategoryAndIsPrivateFalseOrderByCreatedAtDesc(Category category);

    @Query("SELECT d FROM DiaryEntity d WHERE d.category = :category AND d.isPrivate = false ORDER BY LENGTH(d.content) DESC")
    Optional<List<DiaryEntity>> findByCategoryAndIsPrivateFalseOrderByContentLengthDesc(@Param("category") Category category);


    //카테고리 필터링  - MyDiary
    Optional<List<DiaryEntity>> findByUserIdAndCategoryOrderByCreatedAtDesc(long userId, Category category);

    @Query("SELECT d FROM DiaryEntity d WHERE d.category = :category AND d.userId = :userId ORDER BY LENGTH(d.content) DESC")
    Optional<List<DiaryEntity>> findByUserIdAndCategoryOrderByContentLengthDesc(@Param("userId") long userId, @Param("category") Category category);


    //카테고리 필터링 X - 전체 조회
    Optional<List<DiaryEntity>> findAllByIsPrivateFalseOrderByCreatedAtDesc();

    @Query("SELECT d FROM DiaryEntity d WHERE d.isPrivate = false ORDER BY LENGTH(d.content) DESC")
    Optional<List<DiaryEntity>> findAllByIsPrivateFalseOrderByContentLengthDesc();


    //카테고리 필터링 X - MyDiary
    @Query("SELECT d FROM DiaryEntity d WHERE d.userId = :userId ORDER BY LENGTH(d.content) DESC")
    Optional<List<DiaryEntity>> findByUserIdOrderByContentLengthDesc(@Param("userId")  long userId);

    Optional<List<DiaryEntity>> findByUserIdOrderByCreatedAtDesc(@Param("userId") long userId);
}
