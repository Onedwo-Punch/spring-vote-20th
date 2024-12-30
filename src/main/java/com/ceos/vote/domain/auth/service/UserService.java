package com.ceos.vote.domain.auth.service;

import com.ceos.vote.domain.auth.JwtToken;
import com.ceos.vote.domain.auth.JwtTokenProvider;
import com.ceos.vote.domain.auth.dto.SignUpDto;
import com.ceos.vote.domain.auth.dto.UserDto;
import com.ceos.vote.domain.users.dto.response.UserResponseDto;
import com.ceos.vote.domain.users.entity.Users;
import com.ceos.vote.domain.users.repository.UserRepository;
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
                users.getUserPart(),
                users.getUserTeam(),
                users.getUsername()
        );
    }

    @Transactional
    //@Override
    public UserDto signUp(SignUpDto signUpDto){
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            throw new IllegalArgumentException("이미 사용 중인 사용자 이름입니다.");
        }

        //password 암호화
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        List<String> roles = new ArrayList<>();
        roles.add("USER"); //user 권한 부여
        return UserDto.toDto(userRepository.save(signUpDto.toEntity(encodedPassword, roles)));
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
