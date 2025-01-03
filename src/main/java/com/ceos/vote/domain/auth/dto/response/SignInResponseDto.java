package com.ceos.vote.domain.auth.dto.response;

import com.ceos.vote.domain.users.entity.Users;
import com.ceos.vote.domain.users.enumerate.Part;
import com.ceos.vote.domain.users.enumerate.Team;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
public class SignInResponseDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Part part;
    private Boolean is_voting_leader;
    private Boolean is_voting_team;

    public static SignInResponseDto from(JwtToken jwtToken, Boolean is_voting_leader, Boolean is_voting_team) {
        return SignInResponseDto.builder()
                .grantType(jwtToken.getGrantType())
                .accessToken(jwtToken.getAccessToken())
                .refreshToken(jwtToken.getRefreshToken())
                .userPart(users.getUserPart())
                .isVotingLeader(isVotingLeader)
                .isVotingTeam(is_voting_team)
                .build();
}