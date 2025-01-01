package com.ceos.vote.domain.auth.service;

import com.ceos.vote.domain.auth.JwtToken;
import com.ceos.vote.domain.auth.JwtTokenProvider;
import com.ceos.vote.domain.auth.dto.request.SignUpDto;
import com.ceos.vote.domain.auth.dto.response.UserInfoDto;
import com.ceos.vote.domain.users.dto.response.UserResponseDto;
import com.ceos.vote.domain.users.entity.Users;
import com.ceos.vote.domain.users.repository.UserRepository;
import com.ceos.vote.global.exception.ApplicationException;
import com.ceos.vote.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class UserService {
    
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public static UserResponseDto getUserInfo(String username){
        Users users = UserRepository.findByUsername(username).orElse(null);

        return new UserResponseDto(
                users.getUserPart().name(),
                users.getUserTeam().name(),
                users.getUsername()
        );
    }

    @Transactional
    //@Override
    public UserInfoDto signUp(SignUpDto signUpDto){

        // 사용자 이름 중복 확인
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            throw new IllegalArgumentException("이미 사용 중인 사용자 이름입니다.");
        }

        // 사용자 이메일 중복 확인
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        log.debug("PASSWORD{}", signUpDto.getPassword());

        //비밀번호 유효성 검사
        if (signUpDto.getPassword() == null || signUpDto.getPassword().isEmpty()) {
        throw new ApplicationException(ExceptionCode.INVALID_PASSWORD);
        }

        //password 암호화
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        log.debug("encodedPassword{}", encodedPassword);

        //사용자 저장
        Users newUser = userRepository.save(signUpDto.toEntity(encodedPassword));
        return UserInfoDto.from(newUser);
    }

    @Transactional
    public JwtToken signIn(String username, String password){
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }
}
