package org.sopt.Diary.repository;

import org.sopt.Diary.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByLoginIdAndPassword(String loginId, String password);
    // Null 이 올 수 있는 객체는 Optional 을 통해 감싸줌
}
