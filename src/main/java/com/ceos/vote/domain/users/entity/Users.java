package com.ceos.vote.domain.users.entity;

import com.ceos.vote.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 32)
    private String username;

    @Column
    private String email;

    @Builder
    public Users(String username, String email) {
        this.username = username;
        this.email = email;
    }
}