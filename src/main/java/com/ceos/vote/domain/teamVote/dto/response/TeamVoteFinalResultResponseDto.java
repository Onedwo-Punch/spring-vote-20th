package com.ceos.vote.domain.teamVote.dto.response;

import java.util.List;

public record TeamVoteFinalResultResponseDto(
        Long total_count,
        List<TeamResultResponseDto> results
){
    public static TeamVoteFinalResultResponseDto from(Long total_count, List<TeamResultResponseDto> results){
        return new TeamVoteFinalResultResponseDto(
                total_count,
                results
        );
    }
}
