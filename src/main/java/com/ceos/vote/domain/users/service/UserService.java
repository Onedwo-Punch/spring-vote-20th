package com.ceos.vote.domain.users.service;

import com.ceos.vote.domain.users.entity.User;
import com.ceos.vote.domain.users.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto getUserInfo(Long userId){
        User user = userRepository.findById(userId).orElse(null);

        return new UserResponseDto(
                user.getUserPart(),
                user.getUserTeam(),
                user.getUsername()
        );
    }
}
