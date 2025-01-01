package com.ceos.vote.domain.users.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String username;
    private String userPart;
    private String userTeam;
}
