package com.ceos.vote.domain.leaderVote.dto.response;

import com.ceos.vote.domain.leaderVote.entity.LeaderVote;
import jakarta.validation.constraints.NotNull;

public record LeaderVoteByUserResponseDto (
        @NotNull
        Boolean is_voting,
        Long leader_Candidate_id
){
    public static LeaderVoteByUserResponseDto from(LeaderVote leaderVote, Boolean is_voting){
        return new LeaderVoteByUserResponseDto(
            is_voting,
            leaderVote.getLeaderCandidate().getId()
        );
    }
}
