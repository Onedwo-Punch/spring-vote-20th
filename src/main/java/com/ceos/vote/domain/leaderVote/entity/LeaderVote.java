package com.ceos.vote.domain.leaderVote.entity;

import com.ceos.vote.domain.leaderCandidate.entity.LeaderCandidate;
import com.ceos.vote.domain.users.entity.Users;
import com.ceos.vote.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class LeaderVote extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leader_vote_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_candidate_id")
    private LeaderCandidate leaderCandidate;

    public void setLeaderCandidate(LeaderCandidate leaderCandidate) {
        this.leaderCandidate = leaderCandidate;
    }

    @Builder
    public LeaderVote(Users user, LeaderCandidate leaderCandidate) {
        this.user = user;
        this.leaderCandidate = leaderCandidate;
    }
}