package com.ceos.vote.domain.leaderVote.dto.response;

import com.ceos.vote.domain.users.enumerate.Part;

import java.util.List;

public record LeaderVoteFinalResultResponseDto (
        String part,
        Long total_count,
        List<LeaderResultResponseDto> results
){
    public static LeaderVoteFinalResultResponseDto from(Part part, Long total_count, List<LeaderResultResponseDto> results){
        return new LeaderVoteFinalResultResponseDto(
                part.getDescription(), 
                total_count,
                results
        );
    }
}
