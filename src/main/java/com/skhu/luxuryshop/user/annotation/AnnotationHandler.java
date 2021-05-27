package com.skhu.luxuryshop.user.annotation;

import com.skhu.luxuryshop.user.exception.FailToAuthenticationException;

public class AnnotationHandler {
    public static boolean checkAuthority(String[] authorities, PreAuthorize preAuthorize) {
        for (String authority : authorities) {
            for (String role : preAuthorize.roles()) {
                if (authority.equals(role)) {
                    return true;
                }
            }
        }
        throw new FailToAuthenticationException();
    }
}