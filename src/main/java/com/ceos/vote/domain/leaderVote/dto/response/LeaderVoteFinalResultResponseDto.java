package com.ceos.vote.domain.leaderVote.dto.response;

import java.util.List;

public record LeaderVoteFinalResultResponseDto (
        Long total_count,
        List<LeaderResultResponseDto> results
){
    public static LeaderVoteFinalResultResponseDto from(Long total_count, List<LeaderResultResponseDto> results){
        return new LeaderVoteFinalResultResponseDto(
                total_count,
                results
        );
    }
}
