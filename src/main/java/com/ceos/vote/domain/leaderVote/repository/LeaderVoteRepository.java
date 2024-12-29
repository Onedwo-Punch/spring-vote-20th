package com.ceos.vote.domain.leaderVote.repository;

import com.ceos.vote.domain.leaderVote.entity.LeaderVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaderVoteRepository extends JpaRepository<LeaderVote, Long> {
}