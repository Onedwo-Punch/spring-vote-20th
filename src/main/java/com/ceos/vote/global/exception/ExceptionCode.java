package com.ceos.vote.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    // 1000: Success Case

    // 2000: Common Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 2000, "서버 에러가 발생하였습니다. 관리자에게 문의해 주세요."),
    NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, 2001, "존재하지 않는 리소스입니다."),
    INVALID_VALUE_EXCEPTION(HttpStatus.BAD_REQUEST, 2002, "올바르지 않은 요청 값입니다."),
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, 2003, "권한이 없는 요청입니다."),
    ALREADY_DELETE_EXCEPTION(HttpStatus.BAD_REQUEST, 2004, "이미 삭제된 리소스입니다."),
    FORBIDDEN_EXCEPTION(HttpStatus.FORBIDDEN, 2005, "인가되지 않는 요청입니다."),
    ALREADY_EXIST_EXCEPTION(HttpStatus.BAD_REQUEST, 2006, "이미 존재하는 리소스입니다."),
    INVALID_SORT_EXCEPTION(HttpStatus.BAD_REQUEST, 2007, "올바르지 않은 정렬 값입니다."),
    BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, 2008, "잘못된 요청입니다."),

    // 3000: USER/Auth
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, 3001, "해당 회원을 찾을 수 없습니다."),
    INVALID_USER_CREDENTIALS(HttpStatus.UNAUTHORIZED, 3002, "잘못된 사용자 인증 정보입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, 3003, "비밀번호를 찾을 수 없습니다."),
    NOT_FOUND_USERNAME(HttpStatus.NOT_FOUND, 3004, "해당 아이디를 찾을 수 없습니다."),

    // 4000: Leader Vote Error
    NOT_FOUND_LEADER_CANDIDATE(HttpStatus.NOT_FOUND, 4001, "해당 leader candidate가 존재하지 않습니다."),
    NOT_FOUND_LEADER_VOTE(HttpStatus.NOT_FOUND, 4002, "해당 leader vote가 존재하지 않습니다."),

    // 5000: Team Vote Error
    NOT_FOUND_TEAM_CANDIDATE(HttpStatus.NOT_FOUND, 4001, "해당 team candidate가 존재하지 않습니다."),
    NOT_FOUND_TEAM_VOTE(HttpStatus.NOT_FOUND, 4002, "해당 team vote가 존재하지 않습니다.");


    // 6000:

    //7000: [임의] Error


    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

}
