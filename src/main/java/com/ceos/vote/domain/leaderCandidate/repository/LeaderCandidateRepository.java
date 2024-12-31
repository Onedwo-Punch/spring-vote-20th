package com.ceos.vote.domain.leaderCandidate.repository;

import com.ceos.vote.domain.leaderCandidate.entity.LeaderCandidate;
import com.ceos.vote.domain.users.enumerate.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaderCandidateRepository extends JpaRepository<LeaderCandidate, Long> {
    List<LeaderCandidate> findByPart(Part part);
}