package com.ceos.vote.domain.leaderVote.dto.response;

public record LeaderResultResponseDto (
        String leader_name,
        Long count
){
    public Long getVoteCount() {
        return count;
    }

    public static LeaderResultResponseDto from(String leader_name, Long count){
        return new LeaderResultResponseDto(
                leader_name,
                count
        );
    }
}
