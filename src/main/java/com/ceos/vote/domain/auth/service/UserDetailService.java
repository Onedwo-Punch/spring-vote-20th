package com.ceos.vote.domain.auth.service;

import com.ceos.vote.domain.users.entity.Users;
import com.ceos.vote.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = null;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserRepository.findByUsername(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(@NotNull Users users) {
        return Users.builder()
                .username(users.getUsername())
                .password(passwordEncoder.encode(users.getPassword()))
                .roles(List.of(users.getRoles().toArray(new String[0])))
                .build();
    }
}
