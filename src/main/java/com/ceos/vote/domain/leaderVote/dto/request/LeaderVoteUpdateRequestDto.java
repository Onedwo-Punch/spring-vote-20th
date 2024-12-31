package com.ceos.vote.domain.leaderVote.dto.request;

import com.ceos.vote.domain.leaderCandidate.entity.LeaderCandidate;
import jakarta.validation.constraints.NotNull;

public record LeaderVoteUpdateRequestDto(
        @NotNull
        Long leader_candidate_id
){
}