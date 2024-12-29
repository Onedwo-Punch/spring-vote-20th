package com.ceos.vote.domain.leaderVote.controller;

import com.ceos.vote.domain.leaderVote.dto.request.LeaderVoteCreateRequestDto;
import com.ceos.vote.domain.leaderVote.dto.request.LeaderVoteUpdateRequestDto;
import com.ceos.vote.domain.leaderVote.dto.response.LeaderVoteByUserResponseDto;
import com.ceos.vote.domain.leaderVote.dto.response.LeaderVoteFinalResultResponseDto;
import com.ceos.vote.domain.leaderVote.dto.response.PartCandidateResponseDto;
import com.ceos.vote.domain.leaderVote.service.LeaderVoteService;
import com.ceos.vote.global.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Leader Vote", description = "leader vote api")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/leader")
public class LeaderVoteController {
    private final LeaderVoteService leaderVoteService;

    @Operation(summary = "leader candidate 전체 조회")
    @GetMapping("/candidate")
    public CommonResponse<List<PartCandidateResponseDto>> getLeaderCandidates(){
        final List<PartCandidateResponseDto> leaderCandidates = leaderVoteService.findLeaderCandidates();
        return new CommonResponse<>(leaderCandidates, "전체 파트장 후보 조회를 성공하였습니다.");
    }

    @Operation(summary = "leader vote 생성")
    @PostMapping
    public CommonResponse<Void> createLeaderVote(@RequestBody LeaderVoteCreateRequestDto requestDto){
        leaderVoteService.createLeaderVote(requestDto);
        return new CommonResponse<>("new leader vote를 생성하였습니다.");
    }

    @Operation(summary = "leader vote 전체 결과 조회")
    @GetMapping
    public CommonResponse<List<LeaderVoteFinalResultResponseDto>> getLeaderVoteResult(){
        final List<LeaderVoteFinalResultResponseDto> results = leaderVoteService.getLeaderVoteFinalResult();
        return new CommonResponse<>(results, "전체 투표 결과 조회를 성공하였습니다.");
    }

    @Operation(summary = "user의 leader vote 조회")
    @GetMapping("/{userId}")
    public CommonResponse<LeaderVoteByUserResponseDto> getLeaderVoteByUser(@PathVariable Long userId){
        final LeaderVoteByUserResponseDto leaderVoteByUser = leaderVoteService.getLeaderVoteByUser(userId);
        return new CommonResponse<>(leaderVoteByUser, "해당 user의 투표 결과 조회에 성공하였습니다.");
    }

    @Operation(summary = "user의 leader vote 수정")
    @PutMapping("/{userId}")
    public CommonResponse<Void> updateLeaderVote(@PathVariable Long userId, @RequestBody LeaderVoteUpdateRequestDto requestDto){
        leaderVoteService.updateLeaderVoteByUser(userId, requestDto);
        return new CommonResponse<>("leader vote 수정을 성공하였습니다.");
    }

}
