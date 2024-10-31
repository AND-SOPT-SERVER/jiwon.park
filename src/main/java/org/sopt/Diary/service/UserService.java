package org.sopt.Diary.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.sopt.Diary.dto.req.SignInReq;
import org.sopt.Diary.dto.req.SignUpReq;

import org.sopt.Diary.entity.UserEntity;
import org.sopt.Diary.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional // 클래스의 메소드 호출시 트랜잭션 시작, 메소드 종료시 커밋, 예외 발생시 롤백
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;}

    public void join(@Valid final SignUpReq signUpRequest) {
        //중복회원 검증
        validateDuplicateMember(signUpRequest.loginId(), signUpRequest.password());
       // SignUpReq -> UserEntity
        final UserEntity newUserEntity =  UserEntity.createUser(signUpRequest.loginId(), signUpRequest.password(), signUpRequest.nickname());
        userRepository.save(newUserEntity);
    }

    private void validateDuplicateMember(final String loginId, final String password) {
        Optional<UserEntity> findUser = userRepository.findByLoginIdAndPassword(loginId, password);
        if(findUser.isPresent()){
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    public Long login(@Valid final SignInReq signInReq) {

        // 1. userRepository 에서 Id 찾기 -> NPE 방지 위해 Optional 사용
        UserEntity findUser = userRepository.findByLoginIdAndPassword(signInReq.loginId(), signInReq.password())
                .orElseThrow(()-> new IllegalStateException("로그인 정보가 올바르지 않습니다"));
        // 2. UserID 반환
        return findUser.getId();
    }

    // UserService.java
    public UserEntity findByUserId(final long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자입니다."));
    }

}
