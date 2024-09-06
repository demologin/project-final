package com.javarush.jira.common.jwt.converter;

import com.javarush.jira.common.jwt.Token;
import com.javarush.jira.common.jwt.deserializer.AccessTokenJwsStringDeserializer;
import com.javarush.jira.common.jwt.deserializer.RefreshTokenJweStringDeserializer;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationConverter implements AuthenticationConverter {

    private final AccessTokenJwsStringDeserializer accessTokenStringDeserializer;
    private final RefreshTokenJweStringDeserializer refreshTokenStringDeserializer;

    @Override
    public Authentication convert(HttpServletRequest request) {
        var authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader != null && !authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.replace("Bearer ", "");
            Token accessToken = accessTokenStringDeserializer.apply(token);
            if(accessToken != null) {
                return new UsernamePasswordAuthenticationToken(accessToken, token);
            }

            Token refreshToken = refreshTokenStringDeserializer.apply(token);
            if(refreshToken != null) {
                return new UsernamePasswordAuthenticationToken(refreshToken, token);
            }
        }
        return null;
    }
}
