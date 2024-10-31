package org.sopt.Diary.controller;

import jakarta.validation.Valid;
import org.sopt.Diary.dto.req.SignInReq;
import org.sopt.Diary.dto.req.SignUpReq;
import org.sopt.Diary.dto.res.UserRes;
import org.sopt.Diary.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(final  UserService userService){this.userService=userService;}

    /**
     * 회원가입
     * @param signUpRequest  Id, Pwd, Nickname
     */
    @PostMapping("/signup")
    private void signUp(@Valid @RequestBody SignUpReq signUpRequest){
        // 제약사항 따로 없음
        //@Valid 를 통해 RequestBody @NotNull 체크해줌
        userService.join(signUpRequest);
    }

    /**
     * 로그인
     * @param signInReq Id, Pwd
     * @return 200
     */
    @PostMapping("/signin")
    private UserRes signIn(@Valid @RequestBody SignInReq signInReq){
        Long userId= userService.login(signInReq);
        return new UserRes(userId);

    }


}
