package com.ceos.vote.domain.leaderCandidate.service;

import com.ceos.vote.domain.leaderCandidate.entity.LeaderCandidate;
import com.ceos.vote.domain.leaderCandidate.repository.LeaderCandidateRepository;
import com.ceos.vote.global.exception.ApplicationException;
import com.ceos.vote.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LeaderCandidateService {

    private final LeaderCandidateRepository leaderCandidateRepository;

    public LeaderCandidate findLeaderCandidateById(Long id) {
        return leaderCandidateRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionCode.NOT_FOUND_LEADER_CANDIDATE));
    }

}
