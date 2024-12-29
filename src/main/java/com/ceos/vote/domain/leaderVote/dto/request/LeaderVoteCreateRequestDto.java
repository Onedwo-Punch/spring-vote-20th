package com.ceos.vote.domain.leaderVote.dto.request;

import com.ceos.vote.domain.leaderCandidate.entity.LeaderCandidate;
import com.ceos.vote.domain.leaderVote.entity.LeaderVote;
import com.ceos.vote.domain.users.entity.Users;

public record LeaderVoteCreateRequestDto (
        Users user,
        LeaderCandidate leaderCandidate
){
    public LeaderVote toEntity(){
        return LeaderVote.builder()
                .user(user)
                .leaderCandidate(leaderCandidate)
                .build();
    }
}
