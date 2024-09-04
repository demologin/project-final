package com.javarush.jira.common.jwt.filter;

import com.javarush.jira.common.jwt.Token;
import com.javarush.jira.common.jwt.Tokens;
import com.javarush.jira.common.jwt.factory.DefaultAccessTokenFactory;
import com.javarush.jira.common.jwt.factory.DefaultRefreshTokenFactory;
import com.javarush.jira.common.jwt.serializer.Serializer;
import com.javarush.jira.common.util.JsonUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Setter
@Component
public class RequestJwtTokensFilter extends OncePerRequestFilter {

    private RequestMatcher requestMatcher = new AntPathRequestMatcher("/jwt/tokens", HttpMethod.POST.name());
    private SecurityContextRepository securityContextRepository;

    private DefaultRefreshTokenFactory refreshTokenFactory;
    private DefaultAccessTokenFactory accessTokenFactory;

    private Serializer<Token, String> accessTokenStringSerializer;
    private Serializer<Token, String> refreshTokenStringSerializer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(requestMatcher.matches(request)) {
            if(securityContextRepository.containsContext(request)) {
                SecurityContext context = securityContextRepository.loadDeferredContext(request).get();
                if(context != null && context.getAuthentication() instanceof PreAuthenticatedAuthenticationToken) {
                    Token refreshToken = refreshTokenFactory.apply(context.getAuthentication());
                    Token accesToken = accessTokenFactory.apply(refreshToken);

                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    JsonUtil.writeValue(response.getWriter(), new Tokens(accessTokenStringSerializer.apply(accesToken),
                            accesToken.expiresAt().toString(), refreshTokenStringSerializer.apply(refreshToken),
                            refreshToken.expiresAt().toString()));
                    return;
                }
            }
            throw new AccessDeniedException("User must be authenticated");
        }

        filterChain.doFilter(request, response);
    }
}
