package com.javarush.jira.common.jwt.config;

import com.javarush.jira.common.jwt.Token;
import com.javarush.jira.common.jwt.filter.RequestJwtTokensFilter;
import com.javarush.jira.common.jwt.serializer.Serializer;
import lombok.Builder;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Setter
@Builder
public class JwtAuthenticationConfigurer extends AbstractHttpConfigurer<JwtAuthenticationConfigurer, HttpSecurity> {
    private Serializer<Token, String> accessTokenStringSerializer;
    private Serializer<Token, String> refreshTokenStringSerializer;

    @Override
    public void init(HttpSecurity builder) throws Exception {
        var csrfConfigurer = builder.getConfigurer(CsrfConfigurer.class);
        if(csrfConfigurer != null) {
            csrfConfigurer.ignoringRequestMatchers(new AntPathRequestMatcher("/jwt/tokens", HttpMethod.POST.name()));
        }
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        RequestJwtTokensFilter filter = new RequestJwtTokensFilter();
        builder.addFilterAfter(filter, ExceptionTranslationFilter.class);
        filter.setAccessTokenStringSerializer(accessTokenStringSerializer);
        filter.setRefreshTokenStringSerializer(refreshTokenStringSerializer);
    }
}
