package com.ceos.vote.domain.teamVote.dto.response;

import com.ceos.vote.domain.teamCandidate.entity.TeamCandidate;

public record TeamCandidateResponseDto (
        Long id,
        String name
){
    public static TeamCandidateResponseDto from(TeamCandidate teamCandidate) {
        return new TeamCandidateResponseDto(
                teamCandidate.getId(),
                teamCandidate.getName()
        );
    }
}
