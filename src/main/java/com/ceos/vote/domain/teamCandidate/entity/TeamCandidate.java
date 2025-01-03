package com.ceos.vote.domain.teamCandidate.entity;

import com.ceos.vote.domain.users.enumerate.Team;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TeamCandidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_candidate_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private Team teamName;

    @Builder
    public TeamCandidate(Team name) {
        this.teamName = name;
    }
}
