package com.ceos.vote.domain.teamVote.dto.request;

import com.ceos.vote.domain.teamCandidate.entity.TeamCandidate;
import com.ceos.vote.domain.teamVote.entity.TeamVote;
import com.ceos.vote.domain.users.entity.Users;
import jakarta.validation.constraints.NotNull;

public record TeamVoteCreateRequestDto(
        Long user_id,
        @NotNull
        Long team_candidate_id
){
    public TeamVote toEntity(Users user, TeamCandidate teamCandidate) {
        return TeamVote.builder()
                .user(user)
                .teamCandidate(teamCandidate)
                .build();
    }
}
