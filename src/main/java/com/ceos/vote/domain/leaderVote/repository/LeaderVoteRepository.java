package com.ceos.vote.domain.leaderVote.repository;

import com.ceos.vote.domain.leaderCandidate.entity.LeaderCandidate;
import com.ceos.vote.domain.leaderVote.entity.LeaderVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeaderVoteRepository extends JpaRepository<LeaderVote, Long> {
    Optional<LeaderVote> findByUserId(Long userId);

    Long countByLeaderCandidate(LeaderCandidate leaderCandidate);
}