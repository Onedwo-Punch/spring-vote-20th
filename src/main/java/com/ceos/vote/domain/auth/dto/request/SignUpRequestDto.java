package com.ceos.vote.domain.auth.dto.request;

import com.ceos.vote.domain.users.entity.Users;
import com.ceos.vote.domain.users.enumerate.Part;
import com.ceos.vote.domain.users.enumerate.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequestDto {

    private String username;
    private String email;
    private String password;

    private Team userTeam;
    private Part userPart;

    public Users toEntity(String encodedPassword){
        return Users.builder()
                .username(username)
                .email(email)
                .password(encodedPassword)
                .userTeam(userTeam)
                .userPart(userPart)
                .build();
    }

    public String getPassword() {
        System.out.println("Getter called for password: " + password);
        return password;
    }

}
