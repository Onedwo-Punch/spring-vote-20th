package com.ceos.vote.domain.utils;

import com.ceos.vote.global.exception.ApplicationException;
import com.ceos.vote.global.exception.ExceptionCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName()==null) {
            throw new ApplicationException(ExceptionCode.INVALID_USER_CREDENTIALS);
        }
        return authentication.getName();
    }
}
