package com.ceos.vote.domain.teamVote.service;

import com.ceos.vote.domain.auth.service.UserDetailService;
import com.ceos.vote.domain.leaderVote.service.LeaderVoteService;
import com.ceos.vote.domain.teamCandidate.entity.TeamCandidate;
import com.ceos.vote.domain.teamCandidate.service.TeamCandidateService;
import com.ceos.vote.domain.teamVote.dto.request.TeamVoteCreateRequestDto;
import com.ceos.vote.domain.teamVote.dto.request.TeamVoteUpdateRequestDto;
import com.ceos.vote.domain.teamVote.dto.response.TeamResultResponseDto;
import com.ceos.vote.domain.teamVote.dto.response.TeamVoteByUserResponseDto;
import com.ceos.vote.domain.teamVote.dto.response.TeamVoteFinalResultResponseDto;
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

import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamVoteService {
    private final TeamVoteRepository teamVoteRepository;
    private final TeamCandidateRepository teamCandidateRepository;
    private final TeamCandidateService teamCandidateService;
    private final UserDetailService userDetailService;

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
        Users user = userDetailService.findUserById(requestDto.user_id());
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

    /*
    user의 teamVote 수정
     */
    @Transactional
    public void updateTeamVoteByUser(final Long userId, final TeamVoteUpdateRequestDto requestDto) {
        TeamVote teamVote = findTeamVoteByUserId(userId);

        TeamCandidate teamCandidate = teamCandidateService.findTeamCandidateById(requestDto.team_candidate_id());

        teamVote.setTeamCandidate(teamCandidate);
        teamVoteRepository.save(teamVote);

    }

    /*
    팀별 teamVote 결과 조회
     */
    public List<TeamResultResponseDto> getTeamResult() {
        // 모든 후보팀 조회
        List<TeamCandidate> teamCandidates = teamCandidateRepository.findAll();

        // 각 후보의 투표 결과 반환 및 득표수 내림차순 정렬
        return teamCandidates.stream()
                .map(candidate -> TeamResultResponseDto.from(candidate.getTeamName().getDescription(), teamVoteRepository.countByTeamCandidate(candidate)))
                .sorted(Comparator.comparingLong(TeamResultResponseDto::getVoteCount).reversed()) // 내림차순 정렬
                .toList();
    }

    /*
    teamVote 전체 결과 조회
     */
    public TeamVoteFinalResultResponseDto getTeamVoteFinalResult(){
        List<TeamResultResponseDto> results= getTeamResult();
        Long total_count = teamVoteRepository.count();

        return TeamVoteFinalResultResponseDto.from(total_count, results);
    }
}
