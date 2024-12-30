package com.ceos.vote.domain.users.dto;

import com.ceos.vote.domain.users.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpDto {

    private String username;
    private String email;
    private String password;
    private String userTeam;
    private String userPart;
    private List<String> roles = new ArrayList<>();

    public User toEntity(String encodedPassword, List<String> roles){
        return User.builder()
                .username(username)
                .email(email)
                .password(encodedPassword)
                .userTeam(userTeam)
                .userPart(userPart)
                .roles(roles)
                .build();
    }


}
