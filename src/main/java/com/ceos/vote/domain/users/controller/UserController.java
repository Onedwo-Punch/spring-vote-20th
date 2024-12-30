package com.ceos.vote.domain.users.controller;

import com.ceos.vote.domain.auth.JwtToken;
import com.ceos.vote.domain.users.dto.SignInDto;
import com.ceos.vote.domain.users.dto.SignUpDto;
import com.ceos.vote.domain.users.dto.UserDto;
import com.ceos.vote.domain.users.service.UserService;
import com.ceos.vote.domain.utils.SecurityUtil;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto) {
        UserDto userDto = userService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/sign-in")
    public JwtToken signIn(@RequestBody SignInDto signinDto){

        String username = signinDto.getUsername();
        String password = signinDto.getPassword();
        JwtToken jwtToken = userService.signIn(username, password);

        log.info("request username = {}, password = {}", username, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());

    return jwtToken;
    }

    @PostMapping("/test")
    public String test(){
        return SecurityUtil.getCurrentUsername();
    }
}
