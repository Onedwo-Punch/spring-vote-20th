package com.ceos.vote.domain.leaderVote.dto.request;

import com.ceos.vote.domain.leaderCandidate.entity.LeaderCandidate;
import com.ceos.vote.domain.leaderVote.entity.LeaderVote;
import com.ceos.vote.domain.users.entity.Users;
import jakarta.validation.constraints.NotNull;

public record LeaderVoteCreateRequestDto (
        @NotNull
        Users user,
        @NotNull
        LeaderCandidate leaderCandidate
){
    public LeaderVote toEntity(){
        return LeaderVote.builder()
                .user(user)
                .leaderCandidate(leaderCandidate)
                .build();
    }
}
