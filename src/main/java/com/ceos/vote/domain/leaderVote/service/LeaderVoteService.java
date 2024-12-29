package com.ceos.vote.domain.leaderVote.service;

import com.ceos.vote.domain.leaderVote.dto.request.LeaderVoteCreateRequestDto;
import com.ceos.vote.domain.leaderVote.entity.LeaderVote;
import com.ceos.vote.domain.leaderVote.repository.LeaderVoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LeaderVoteService {
    private LeaderVoteRepository leaderVoteRepository;

    /*
    leaderVote 생성
     */
    @Transactional
    public void createLeaderVote(LeaderVoteCreateRequestDto requestDto) {
        final LeaderVote leaderVote = requestDto.toEntity();
        leaderVoteRepository.save(leaderVote);
    }
}
