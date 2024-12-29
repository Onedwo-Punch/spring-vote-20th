package com.ceos.vote.domain.leaderCandidate.entity;

import com.ceos.vote.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LeaderCandidate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leader_candidate_id")
    private Long id;

    @Column(length = 32)
    private String part;

    @Column
    private String name;

    @Builder
    public LeaderCandidate(String part, String name) {
        this.part = part;
        this.name = name;
    }
}