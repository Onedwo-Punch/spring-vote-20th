package com.ceos.vote.domain.leaderVote.dto.response;

import com.ceos.vote.domain.leaderCandidate.entity.LeaderCandidate;

public record LeaderCandidateResponseDto (
        Long id,
        String name
){
    public static LeaderCandidateResponseDto from(LeaderCandidate leaderCandidate) {
        return new LeaderCandidateResponseDto(
                leaderCandidate.getId(),
                leaderCandidate.getName()
        );
    }
}
