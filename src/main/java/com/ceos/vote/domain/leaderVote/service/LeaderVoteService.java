package com.ceos.vote.domain.leaderVote.service;

import com.ceos.vote.domain.leaderVote.dto.request.LeaderVoteCreateRequestDto;
import com.ceos.vote.domain.leaderVote.dto.response.LeaderVoteByUserResponseDto;
import com.ceos.vote.domain.leaderVote.entity.LeaderVote;
import com.ceos.vote.domain.leaderVote.repository.LeaderVoteRepository;
import com.ceos.vote.global.exception.ApplicationException;
import com.ceos.vote.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LeaderVoteService {
    private LeaderVoteRepository leaderVoteRepository;

    public LeaderVote findLeaderVoteById(Long id) {
        return leaderVoteRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionCode.NOT_FOUND_LEADER_VOTE));
    }
    /*
    leaderVote 생성
     */
    @Transactional
    public void createLeaderVote(LeaderVoteCreateRequestDto requestDto) {
        final LeaderVote leaderVote = requestDto.toEntity();
        leaderVoteRepository.save(leaderVote);
    }

    /*
    leaderVote 전체 결과 조회
     */
    public LeaderVoteByUserResponseDto getLeaderVoteByUser(final Long userId) {
        try {
            final LeaderVote leaderVote = findLeaderVoteById(userId);
            return LeaderVoteByUserResponseDto.from(leaderVote, true);
        } catch (Exception e) {
            return LeaderVoteByUserResponseDto.from(null, false);
        }
    }
}
