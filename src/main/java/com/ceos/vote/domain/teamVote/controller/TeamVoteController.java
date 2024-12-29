package com.ceos.vote.domain.teamVote.controller;

import com.ceos.vote.domain.teamVote.dto.request.TeamVoteCreateRequestDto;
import com.ceos.vote.domain.teamVote.service.TeamVoteService;
import com.ceos.vote.domain.teamVote.dto.response.TeamCandidateResponseDto;
import com.ceos.vote.global.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Team Vote", description = "team vote api")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/team")
public class TeamVoteController {
    private final TeamVoteService teamVoteService;

    @Operation(summary = "team candidate 전체 조회")
    @GetMapping("/candidate")
    public CommonResponse<List<TeamCandidateResponseDto>> getTeamCandidates(){
        final List<TeamCandidateResponseDto> teamCandidates = teamVoteService.findTeamCandidates();
        return new CommonResponse<>(teamCandidates, "전체 후보팀 조회를 성공하였습니다.");
    }

    @Operation(summary = "team vote 생성")
    @PostMapping
    public CommonResponse<Void> createTeamVote(@RequestBody TeamVoteCreateRequestDto requestDto){
        teamVoteService.createTeamVote(requestDto);
        return new CommonResponse<>("new team vote를 생성하였습니다.");
    }
}
