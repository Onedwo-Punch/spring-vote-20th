package com.ceos.vote.domain.users.enumerate;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Team {

    CUPFEEDEAL("CUPFEEDEAL"),
    MUSAI("MUSAI"),
    CAKEWAY("CAKEWAY"),
    PHOTOGROUND("PHOTOGROUND"),
    ANGELBRIDGE("ANGELBRIDGE");    ;

    private final String description;

    Team(String description) {
        this.description = description;
    }

}
