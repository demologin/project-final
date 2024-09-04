package com.javarush.jira.common.jwt.factory;

import com.javarush.jira.common.jwt.Token;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.UUID;

@Setter
@Component
public class DefaultRefreshTokenFactory implements Factory<Authentication, Token> {
    private Duration tokenTtl = Duration.ofDays(1);

    @Override
    public Token apply(Authentication authentication) {
        LinkedList<String> authorities = new LinkedList<>();
        authorities.add("JWT_REFRESH");
        authorities.add("JWT_LOGOUT");

        authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .map(authority -> "GRANT_" + authority)
                .forEach(authorities::add);

        Instant now = Instant.now();
        return new Token(UUID.randomUUID(), authentication.getName(), authorities, now, now.plus(tokenTtl));
    }
}
