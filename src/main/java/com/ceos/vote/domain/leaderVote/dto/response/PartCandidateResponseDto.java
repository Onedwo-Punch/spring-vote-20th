package com.ceos.vote.domain.leaderVote.dto.response;

import com.ceos.vote.domain.users.enumerate.Part;

import java.util.List;

public record PartCandidateResponseDto (
        String part,
        List<LeaderCandidateResponseDto> leaderCandidates
){
    public static PartCandidateResponseDto from(String part, List<LeaderCandidateResponseDto> leaderCandidates) {
        return new PartCandidateResponseDto(
                part,
                leaderCandidates
        );
    }
}
