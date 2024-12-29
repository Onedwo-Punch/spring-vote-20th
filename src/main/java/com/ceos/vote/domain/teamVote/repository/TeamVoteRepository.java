package com.ceos.vote.domain.teamVote.repository;

import com.ceos.vote.domain.teamVote.entity.TeamVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamVoteRepository extends JpaRepository<TeamVote, Long> {
    Optional<TeamVote> findByUserId(Long userId);

}