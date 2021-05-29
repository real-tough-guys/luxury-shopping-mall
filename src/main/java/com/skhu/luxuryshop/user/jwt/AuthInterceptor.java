package com.skhu.luxuryshop.user.jwt;

import com.skhu.luxuryshop.user.annotation.AnnotationHandler;
import com.skhu.luxuryshop.user.annotation.PreAuthorize;
import com.skhu.luxuryshop.user.exception.NoTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        HandlerMethod method = (HandlerMethod) handler;
        Optional<PreAuthorize> preAuthorize = getPreAuthorize(method);
        Optional<String> jwt = resolveToken(request);

        if (preAuthorize.isPresent()) {
            if (jwt.isPresent()) {
                String token = jwt.get();
                tokenProvider.validateToken(token);
                return AnnotationHandler.checkAuthority(getAuthorities(token), preAuthorize.get());
            }
            throw new NoTokenException();
        }
        return true;
    }

    private Optional<String> resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }

    private String[] getAuthorities(String token) {
        String authorities = (String) tokenProvider.getData(token).get("auth");
        return authorities.split(", ");
    }

    private Optional<PreAuthorize> getPreAuthorize(HandlerMethod method) {
        for (Annotation annotation : method.getMethod().getAnnotations()) {
            if (annotation instanceof PreAuthorize) {
                return Optional.of((PreAuthorize) annotation);
            }
        }
        return Optional.empty();
    }
}