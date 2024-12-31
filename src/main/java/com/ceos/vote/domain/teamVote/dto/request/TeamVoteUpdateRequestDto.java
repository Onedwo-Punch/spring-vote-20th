package com.ceos.vote.domain.teamVote.dto.request;

import jakarta.validation.constraints.NotNull;

public record TeamVoteUpdateRequestDto(
        @NotNull
        Long team_candidate_id
){
}