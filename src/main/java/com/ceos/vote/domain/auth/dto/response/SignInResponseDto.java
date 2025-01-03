package com.ceos.vote.domain.auth.dto.response;


import com.ceos.vote.domain.auth.JwtToken;
import com.ceos.vote.domain.users.entity.Users;
import com.ceos.vote.domain.users.enumerate.Part;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class SignInResponseDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Part userPart;
    private Boolean isVotingLeader;
    private Boolean isVotingTeam;

    public static SignInResponseDto from(JwtToken jwtToken, Part userPart, Boolean isVotingLeader, Boolean isVotingTeam) {
        return SignInResponseDto.builder()
                .grantType(jwtToken.getGrantType())
                .accessToken(jwtToken.getAccessToken())
                .refreshToken(jwtToken.getRefreshToken())
                .userPart(userPart)
                .isVotingLeader(isVotingLeader)
                .isVotingTeam(isVotingTeam)
                .build();
    }
}
