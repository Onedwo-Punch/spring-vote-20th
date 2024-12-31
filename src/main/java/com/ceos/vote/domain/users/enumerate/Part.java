package com.ceos.vote.domain.users.enumerate;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Part {

    BE("BE"),
    FE("FE"),
    DESIGN("DESIGN"),
    PLANNING("PLANNING");

    private final String description;

    Part(String description) {
        this.description = description;
    }
}
