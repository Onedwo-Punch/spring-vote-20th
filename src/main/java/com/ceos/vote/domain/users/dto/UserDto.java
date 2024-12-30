package com.ceos.vote.domain.users.dto;

import com.ceos.vote.domain.users.entity.User;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String userTeam;
    private String userPart;

    static public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .userTeam(user.getUserTeam())
                .userPart(user.getUserPart())
                .build();
    }

    public User toEntity() {
        return User.builder()
                .id(id)
                .username(username)
                .email(email)
                .userTeam(userTeam)
                .userPart(userPart)
                .build();
    }
}
