package com.ceos.vote.domain.teamVote.service;

import com.ceos.vote.domain.teamCandidate.entity.TeamCandidate;
import com.ceos.vote.domain.teamCandidate.repository.TeamCandidateRepository;
import com.ceos.vote.domain.teamVote.dto.response.TeamCandidateResponseDto;
import com.ceos.vote.domain.teamVote.repository.TeamVoteRepository;
import com.ceos.vote.domain.users.enumerate.Part;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamVoteService {
    private final TeamVoteRepository teamVoteRepository;
    private final TeamCandidateRepository teamCandidateRepository;

    /*
    전체 team candidate 조회
     */
    public List<TeamCandidateResponseDto> findTeamCandidates() {
        List<TeamCandidate> teamCandidates = teamCandidateRepository.findAll();

        return teamCandidates.stream()
                .map(TeamCandidateResponseDto::from)
                .toList();
    }
}
