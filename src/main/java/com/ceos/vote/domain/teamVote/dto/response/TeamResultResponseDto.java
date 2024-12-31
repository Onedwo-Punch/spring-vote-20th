package com.ceos.vote.domain.teamVote.dto.response;

public record TeamResultResponseDto(
        String team_name,
        Long count
){
    public Long getVoteCount() {
        return count;
    }

    public static TeamResultResponseDto from(String team_name, Long count){
        return new TeamResultResponseDto(
                team_name,
                count
        );
    }
}
