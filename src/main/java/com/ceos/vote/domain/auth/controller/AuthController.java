package com.ceos.vote.domain.auth.controller;

import com.ceos.vote.domain.auth.JwtToken;
import com.ceos.vote.domain.auth.dto.request.SignInRequestDto;
import com.ceos.vote.domain.auth.dto.request.SignUpRequestDto;
import com.ceos.vote.domain.auth.service.UserService;
import com.ceos.vote.domain.utils.SecurityUtil;
import com.ceos.vote.global.common.response.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/sign-up")
    public CommonResponse<Void> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        log.debug("Request body: ", signUpRequestDto);
        userService.signUp(signUpRequestDto);
        return new CommonResponse<>("회원가입에 성공했습니다.");
    }

    @PostMapping("/sign-in")
    public CommonResponse<JwtToken> signIn(@RequestBody SignInRequestDto signinRequestDto){

        String username = signinRequestDto.getUsername();
        String password = signinRequestDto.getPassword();
        JwtToken jwtToken = userService.signIn(username, password);

        AuthController.log.info("request name = {}, password = {}", username, password);
        AuthController.log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

        return new CommonResponse<>(jwtToken, "로그인에 성공헀습니다.");
    }

    @PostMapping("/test")
    public CommonResponse<String> test(){

        String currentUsername = SecurityUtil.getCurrentUsername();

        return new CommonResponse<>(currentUsername, "현재 사용자 정보를 반환합니다.");
    }
}
