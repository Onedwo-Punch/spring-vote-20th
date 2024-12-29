package com.ceos.vote.domain.leaderVote.dto.request;

import com.ceos.vote.domain.leaderCandidate.entity.LeaderCandidate;
import com.ceos.vote.domain.leaderVote.entity.LeaderVote;
import com.ceos.vote.domain.users.entity.Users;

public record LeaderVoteCreateRequestDto (
        Long user_id,
        Long leader_candidate_id
){
    public LeaderVote toEntity(Users user, LeaderCandidate leaderCandidate) {
        return LeaderVote.builder()
                .user(user)
                .leaderCandidate(leaderCandidate)
                .build();
    }
}
