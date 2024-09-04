package com.javarush.jira.common.jwt.factory;

import com.javarush.jira.common.jwt.Token;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Setter
@Component
public class DefaultAccessTokenFactory implements Factory<Token, Token> {

    private Duration tokenTtl = Duration.ofMinutes(5);

    @Override
    public Token apply(Token token) {

        Instant now = Instant.now();
        List<String> authorities = token.authorities().stream()
                .filter(authority -> authority.startsWith("GRANT_"))
                .map(authority -> authority.replace("GRANT_", ""))
                .toList();

        return new Token(token.id(), token.subject(), authorities, now, now.plus(tokenTtl));
    }
}
