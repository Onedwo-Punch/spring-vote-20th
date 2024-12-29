package com.ceos.vote.domain.teamCandidate.entity;

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
    @Column(name = "leader_candidate_id")
    private Long id;

    @Column
    private String name;

    @Builder
    public TeamCandidate(String name) {
        this.name = name;
    }
}
