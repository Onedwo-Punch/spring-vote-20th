package com.ceos.vote.domain.teamVote.entity;

import com.ceos.vote.domain.teamCandidate.entity.TeamCandidate;
import com.ceos.vote.domain.users.entity.Users;
import com.ceos.vote.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TeamVote extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_vote_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_candidate_id")
    private TeamCandidate teamCandidate;

    public void setTeamCandidate(TeamCandidate teamCandidate) {
        this.teamCandidate = teamCandidate;
    }

    @Builder
    public TeamVote(Users user, TeamCandidate teamCandidate) {
        this.user = user;
        this.teamCandidate = teamCandidate;
    }
}