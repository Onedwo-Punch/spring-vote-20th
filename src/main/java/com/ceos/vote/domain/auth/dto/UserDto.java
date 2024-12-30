package com.ceos.vote.domain.auth.dto;

import com.ceos.vote.domain.users.entity.Users;
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

    static public UserDto toDto(Users users) {
        return UserDto.builder()
                .id(users.getId())
                .username(users.getUsername())
                .email(users.getEmail())
                .userTeam(users.getUserTeam())
                .userPart(users.getUserPart())
                .build();
    }

    public Users toEntity() {
        return Users.builder()
                .id(id)
                .username(username)
                .email(email)
                .userTeam(userTeam)
                .userPart(userPart)
                .build();
    }
}
