package com.ceos.vote.domain.teamCandidate.service;

import com.ceos.vote.domain.teamCandidate.entity.TeamCandidate;
import com.ceos.vote.domain.teamCandidate.repository.TeamCandidateRepository;
import com.ceos.vote.global.exception.ApplicationException;
import com.ceos.vote.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamCandidateService {


    private final TeamCandidateRepository teamCandidateRepository;

    public TeamCandidate findTeamCandidateById(Long id) {
        return teamCandidateRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionCode.NOT_FOUND_TEAM_CANDIDATE));
    }

}
