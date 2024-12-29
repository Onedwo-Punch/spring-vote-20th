package com.ceos.vote.domain.leaderVote.service;

import com.ceos.vote.domain.leaderCandidate.entity.LeaderCandidate;
import com.ceos.vote.domain.leaderCandidate.repository.LeaderCandidateRepository;
import com.ceos.vote.domain.leaderCandidate.service.LeaderCandidateService;
import com.ceos.vote.domain.leaderVote.dto.request.LeaderVoteCreateRequestDto;
import com.ceos.vote.domain.leaderVote.dto.request.LeaderVoteUpdateRequestDto;
import com.ceos.vote.domain.leaderVote.dto.response.LeaderVoteByUserResponseDto;
import com.ceos.vote.domain.leaderVote.entity.LeaderVote;
import com.ceos.vote.domain.leaderVote.repository.LeaderVoteRepository;
import com.ceos.vote.domain.users.entity.Users;
import com.ceos.vote.domain.users.repository.UserRepository;
import com.ceos.vote.global.exception.ApplicationException;
import com.ceos.vote.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LeaderVoteService {
    private final UserRepository userRepository;
    private final LeaderCandidateRepository leaderCandidateRepository;
    private final LeaderCandidateService leaderCandidateService;
    private LeaderVoteRepository leaderVoteRepository;

    public LeaderVote findLeaderVoteByUserId(Long id) {
        return leaderVoteRepository.findByUserId(id)
                .orElseThrow(() -> new ApplicationException(ExceptionCode.NOT_FOUND_LEADER_VOTE));
    }

    /*
    leaderVote 생성
     */
    @Transactional
    public void createLeaderVote(LeaderVoteCreateRequestDto requestDto) {
        Users user = userRepository.findById(requestDto.user_id())
                .orElseThrow(() -> new ApplicationException(ExceptionCode.NOT_FOUND_EXCEPTION));

        LeaderCandidate leaderCandidate = leaderCandidateService.findLeaderCandidateById(requestDto.leader_candidate_id());

        final LeaderVote leaderVote = requestDto.toEntity(user, leaderCandidate);
        leaderVoteRepository.save(leaderVote);
    }

    /*
    user의 leaderVote 결과 조회
     */
    public LeaderVoteByUserResponseDto getLeaderVoteByUser(final Long userId) {
        try {
            final LeaderVote leaderVote = findLeaderVoteByUserId(userId);
            return LeaderVoteByUserResponseDto.from(leaderVote, true);
        } catch (Exception e) {
            return LeaderVoteByUserResponseDto.from(null, false);
        }
    }

    /*
    user의 leaderVote 수정
     */
    @Transactional
    public void updateLeaderVoteByUser(final Long userId, final LeaderVoteUpdateRequestDto requestDto) {
        LeaderVote leaderVote = findLeaderVoteByUserId(userId);

        LeaderCandidate leaderCandidate = leaderCandidateService.findLeaderCandidateById(requestDto.leader_candidate_id());

        leaderVote.setLeaderCandidate(leaderCandidate);
        leaderVoteRepository.save(leaderVote);

    }
}
