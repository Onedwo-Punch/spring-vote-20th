package com.ceos.vote.domain.auth.controller;

import com.ceos.vote.domain.auth.JwtToken;
import com.ceos.vote.domain.auth.dto.request.SignInDto;
import com.ceos.vote.domain.auth.dto.request.SignUpDto;
import com.ceos.vote.domain.auth.dto.response.UserInfoDto;
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
import java.util.Map;
import java.util.HashMap;

import static java.rmi.server.LogStream.log;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public CommonResponse<UserInfoDto> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        log.debug("Request body: ", signUpDto);
        UserInfoDto userInfoDto = userService.signUp(signUpDto);
        return new CommonResponse<>(userInfoDto, "회원가입에 성공했습니다.");
    }

    @PostMapping("/sign-in")
    public CommonResponse<JwtToken> signIn(@RequestBody SignInDto signinDto){

        String username = signinDto.getUsername();
        String password = signinDto.getPassword();
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
