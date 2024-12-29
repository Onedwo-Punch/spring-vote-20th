package com.ceos.vote.domain.leaderVote.service;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.ceos.vote.domain.users.enumerate.Part.*;
import static com.ceos.vote.domain.users.enumerate.Part.DESIGN;

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

    // 추후 UserService에 통합 후 삭제
    public Users findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionCode.NOT_FOUND_EXCEPTION));
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
                .map(part -> PartCandidateResponseDto.from(part.getDescription(), findLeaderCandidatesByPart(part)))
                .collect(Collectors.toList());
    }

    /*
    leaderVote 생성
     */
    @Transactional
    public void createLeaderVote(LeaderVoteCreateRequestDto requestDto) {
        Users user = findUserById(requestDto.user_id());
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
    user의 leaderVote 결과 조회
     */
    public LeaderResultResponseDto getLeaderResult(LeaderCandidate leaderCandidate) {
        final String name = leaderCandidate.getName();
        final Long count = leaderVoteRepository.countByLeaderCandidate(leaderCandidate);

        return LeaderResultResponseDto.from(name, count);
    }

    /*
    leaderVote 전체 결과 조회
     */
    public LeaderVoteFinalResultResponseDto getLeaderVoteFinalResult() {
        List<LeaderCandidate> leaderCandidates = leaderCandidateRepository.findAll();
        List<LeaderResultResponseDto> resultList = new ArrayList<>();

        // 각 leader candidate의 결과를 LeaderResultResponseDto로 변환
        leaderCandidates.forEach(leaderCandidate -> resultList.add(getLeaderResult(leaderCandidate)));

        // 총 투표수 조회
        final Long count = leaderVoteRepository.count();

        return LeaderVoteFinalResultResponseDto.from(count, resultList);
    }
}
