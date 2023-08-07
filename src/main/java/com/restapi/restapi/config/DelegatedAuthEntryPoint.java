package com.restapi.restapi.config;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component("delegatedAuthenticationEntryPoint")
public class DelegatedAuthEntryPoint implements AuthenticationEntryPoint {
    private final HandlerExceptionResolver resolver;

    public DelegatedAuthEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        Object authError = request.getAttribute("error");
        if (authError == null) {
            System.out.println(authException.getClass());
        }
        switch (authError) {
            case ExpiredJwtException jwtException ->
                    resolver.resolveException(request, response, null, new ExpiredJwtException(jwtException.getHeader(), jwtException.getClaims(),
                            jwtException.getMessage()));
            case MalformedJwtException malformedJwtException ->
                    resolver.resolveException(request, response, null, new MalformedJwtException(malformedJwtException.getMessage()));
            case SignatureException signatureException ->
                    resolver.resolveException(request, response, null, new SignatureException(signatureException.getMessage()));
            case null, default -> resolver.resolveException(request, response, null, authException);
        }
    }
}