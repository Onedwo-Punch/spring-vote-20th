package com.ceos.vote.domain.auth.dto.response;

import com.ceos.vote.domain.users.entity.Users;
import com.ceos.vote.domain.users.enumerate.Part;
import com.ceos.vote.domain.users.enumerate.Team;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDto {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Team userTeam;
    private Part userPart;

    static public UserInfoDto from(Users users) {
        return UserInfoDto.builder()
                .id(users.getId())
                .password(users.getPassword())
                .username(users.getUsername())
                .email(users.getEmail())
                .userTeam(users.getUserTeam())
                .userPart(users.getUserPart())
                .build();
    }

}
