package com.javarush.jira.common.jwt.config;

import com.javarush.jira.common.jwt.converter.JwtAuthenticationConverter;
import com.javarush.jira.common.jwt.deserializer.AccessTokenJwsStringDeserializer;
import com.javarush.jira.common.jwt.deserializer.RefreshTokenJweStringDeserializer;
import com.javarush.jira.common.jwt.filter.RequestJwtTokensFilter;
import com.javarush.jira.common.jwt.serializer.AccessTokenJwsStringSerializer;
import com.javarush.jira.common.jwt.serializer.RefreshTokenJweStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

@Setter
@AllArgsConstructor
@Builder
@Component
public class JwtAuthenticationConfigurer extends AbstractHttpConfigurer<JwtAuthenticationConfigurer, HttpSecurity> {
    private AccessTokenJwsStringSerializer accessTokenStringSerializer;
    private RefreshTokenJweStringSerializer refreshTokenStringSerializer;

    private AccessTokenJwsStringDeserializer accessTokenStringDeserializer;
    private RefreshTokenJweStringDeserializer refreshTokenStringDeserializer;

    private RequestJwtTokensFilter filter;
    private JwtAuthenticationConverter converter;

    @Override
    public void init(HttpSecurity builder) {
        var csrfConfigurer = builder.getConfigurer(CsrfConfigurer.class);
        if(csrfConfigurer != null) {
            csrfConfigurer.ignoringRequestMatchers(new AntPathRequestMatcher("/api/jwt/tokens", HttpMethod.POST.name()));
        }
    }

    @Override
    public void configure(HttpSecurity builder) {
        AuthenticationFilter jwtAuthenticationFilter = new AuthenticationFilter(builder.getSharedObject(AuthenticationManager.class),
                converter);


        builder.addFilterAfter(filter, ExceptionTranslationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, CsrfFilter.class);
    }
}
