package com.ceos.vote.domain.teamVote.dto.response;

import com.ceos.vote.domain.teamVote.entity.TeamVote;
import jakarta.validation.constraints.NotNull;

public record TeamVoteByUserResponseDto(
        @NotNull
        Boolean is_voting,
        Long team_Candidate_id
){
    public static TeamVoteByUserResponseDto from(TeamVote teamVote, Boolean is_voting){
        return new TeamVoteByUserResponseDto(
                is_voting,
                teamVote != null ? teamVote.getTeamCandidate().getId() : null
        );
    }
}
