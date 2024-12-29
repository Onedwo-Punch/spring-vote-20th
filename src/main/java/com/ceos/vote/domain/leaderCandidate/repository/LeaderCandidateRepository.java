package com.ceos.vote.domain.leaderCandidate.repository;

import com.ceos.vote.domain.leaderCandidate.entity.LeaderCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaderCandidateRepository extends JpaRepository<LeaderCandidate, Long> {
}