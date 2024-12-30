package com.ceos.vote.domain.users.controller;

import com.ceos.vote.domain.auth.service.UserService;
import com.ceos.vote.domain.users.dto.response.UserResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserResponseDto getUserInfo(@PathVariable String username){
        return UserService.getUserInfo(username);
    }
}
