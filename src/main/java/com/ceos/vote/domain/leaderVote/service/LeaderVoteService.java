package com.ceos.vote.domain.leaderVote.service;

import com.ceos.vote.domain.auth.service.UserDetailService;
import com.ceos.vote.domain.auth.service.UserService;
import com.ceos.vote.domain.leaderCandidate.entity.LeaderCandidate;
import com.ceos.vote.domain.leaderCandidate.repository.LeaderCandidateRepository;
import com.ceos.vote.domain.leaderCandidate.service.LeaderCandidateService;
import com.ceos.vote.domain.leaderVote.dto.request.LeaderVoteCreateRequestDto;
import com.ceos.vote.domain.leaderVote.dto.request.LeaderVoteUpdateRequestDto;
import com.ceos.vote.domain.leaderVote.dto.response.*;
import com.ceos.vote.domain.leaderVote.entity.LeaderVote;
import com.ceos.vote.domain.leaderVote.repository.LeaderVoteRepository;
import com.ceos.vote.domain.users.entity.Users;
import com.ceos.vote.domain.users.enumerate.Part;
import com.ceos.vote.domain.users.repository.UserRepository;
import com.ceos.vote.global.exception.ApplicationException;
import com.ceos.vote.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LeaderVoteService {
    private final UserRepository userRepository;
    private final LeaderCandidateRepository leaderCandidateRepository;
    private final LeaderCandidateService leaderCandidateService;
    private final UserDetailService userDetailService;
    private final LeaderVoteRepository leaderVoteRepository;

    public LeaderVote findLeaderVoteByUserId(Long id) {
        return leaderVoteRepository.findByUserId(id)
                .orElseThrow(() -> new ApplicationException(ExceptionCode.NOT_FOUND_LEADER_VOTE));
    }

    /*
    파트별 leader candidate 조회
     */
    public List<LeaderCandidateResponseDto> findLeaderCandidatesByPart(Part part) {
        List<LeaderCandidate> leaderCandidates = leaderCandidateRepository.findByPart(part);

        return leaderCandidates.stream()
                .map(LeaderCandidateResponseDto::from)
                .toList();
    }

    /*
    전체 leader candidate 조회
     */
    public List<PartCandidateResponseDto> findLeaderCandidates() {
        // part별 leader candidate을 조회하여 PartCandidateResponseDto로 반환
        return Arrays.stream(Part.values())
                .map(part -> PartCandidateResponseDto.from(part, findLeaderCandidatesByPart(part)))
                .collect(Collectors.toList());
    }

    /*
    leaderVote 생성
     */
    @Transactional
    public void createLeaderVote(LeaderVoteCreateRequestDto requestDto) {
        Users user = userDetailService.findUserById(requestDto.user_id());
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

    /*
    파트별 leaderVote 결과 조회
     */
    public List<LeaderResultResponseDto> getLeaderResult(Part part) {
        // 파트에 속한 모든 리더 후보 조회
        List<LeaderCandidate> leaderCandidates = leaderCandidateRepository.findByPart(part);

        // 각 후보의 투표 결과 반환 및 득표수 내림차순 정렬
        return leaderCandidates.stream()
                .map(candidate -> LeaderResultResponseDto.from(candidate.getName(), leaderVoteRepository.countByLeaderCandidate(candidate)))
                .sorted(Comparator.comparingLong(LeaderResultResponseDto::getVoteCount).reversed()) // 내림차순 정렬
                .toList();
    }

    /*
    파트별 leaderVote 총 투표수 조회
    */
    public long getTotalVotesByPart(Part part) {
        // 파트에 속한 모든 리더 후보 조회
        List<LeaderCandidate> leaderCandidates = leaderCandidateRepository.findByPart(part);

        // 각 후보의 투표수를 합산
        return leaderCandidates.stream()
                .mapToLong(candidate -> leaderVoteRepository.countByLeaderCandidate(candidate))
                .sum();
    }

    /*
    leaderVote 전체 결과 조회
     */
    public List<LeaderVoteFinalResultResponseDto> getLeaderVoteFinalResult() {

        // part별 투표 결과를 LeaderVoteFinalResultResponseDto로 반환
        return Arrays.stream(Part.values())
                .map(part -> LeaderVoteFinalResultResponseDto.from(part, getTotalVotesByPart(part), getLeaderResult(part)))
                .collect(Collectors.toList());
    }
}
