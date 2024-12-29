package com.ceos.vote.domain.teamVote.service;

import com.ceos.vote.domain.leaderVote.entity.LeaderVote;
import com.ceos.vote.domain.leaderVote.service.LeaderVoteService;
import com.ceos.vote.domain.teamCandidate.entity.TeamCandidate;
import com.ceos.vote.domain.teamCandidate.service.TeamCandidateService;
import com.ceos.vote.domain.teamVote.dto.request.TeamVoteCreateRequestDto;
import com.ceos.vote.domain.teamVote.dto.response.TeamVoteByUserResponseDto;
import com.ceos.vote.domain.teamVote.entity.TeamVote;
import com.ceos.vote.domain.teamCandidate.repository.TeamCandidateRepository;
import com.ceos.vote.domain.teamVote.dto.response.TeamCandidateResponseDto;
import com.ceos.vote.domain.teamVote.repository.TeamVoteRepository;
import com.ceos.vote.domain.users.entity.Users;
import com.ceos.vote.global.exception.ApplicationException;
import com.ceos.vote.global.exception.ExceptionCode;
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
    private final LeaderVoteService leaderVoteService;
    private final TeamCandidateService teamCandidateService;

    public TeamVote findTeamVoteByUserId(Long id) {
        return teamVoteRepository.findByUserId(id)
                .orElseThrow(() -> new ApplicationException(ExceptionCode.NOT_FOUND_TEAM_VOTE));
    }

    /*
    전체 team candidate 조회
     */
    public List<TeamCandidateResponseDto> findTeamCandidates() {
        List<TeamCandidate> teamCandidates = teamCandidateRepository.findAll();

        return teamCandidates.stream()
                .map(TeamCandidateResponseDto::from)
                .toList();
    }

    /*
    teamVote 생성
     */
    @Transactional
    public void createTeamVote(TeamVoteCreateRequestDto requestDto) {
        Users user = leaderVoteService.findUserById(requestDto.user_id());
        TeamCandidate teamCandidate = teamCandidateService.findTeamCandidateById(requestDto.team_candidate_id());

        final TeamVote teamVote = requestDto.toEntity(user, teamCandidate);
        teamVoteRepository.save(teamVote);
    }

    /*
    user의 teamVote 결과 조회
     */
    public TeamVoteByUserResponseDto getTeamVoteByUser(final Long userId) {
        try {
            final TeamVote teamVote = findTeamVoteByUserId(userId);
            return TeamVoteByUserResponseDto.from(teamVote, true);
        } catch (Exception e) {
            return TeamVoteByUserResponseDto.from(null, false);
        }
    }
}
