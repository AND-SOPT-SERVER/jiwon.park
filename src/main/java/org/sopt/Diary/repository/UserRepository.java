package org.sopt.Diary.repository;

import org.sopt.Diary.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByLoginIdAndPassword(String loginId, String password);
}
