package com.ceos.vote.domain.teamCandidate.repository;

import com.ceos.vote.domain.teamCandidate.entity.TeamCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamCandidateRepository extends JpaRepository<TeamCandidate, Long> {
}