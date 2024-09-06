package com.javarush.jira.common.jwt.service;

import com.javarush.jira.common.jwt.Token;
import com.javarush.jira.common.jwt.TokenUser;
import com.javarush.jira.login.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticationUserDetailsService
        implements AuthenticationUserDetailsService<UsernamePasswordAuthenticationToken>{

    @Override
    public UserDetails loadUserDetails(UsernamePasswordAuthenticationToken authenticationToken)
            throws UsernameNotFoundException {
        if (authenticationToken.getPrincipal() instanceof Token token) {
            return new TokenUser(User.builder()
                    .email(token.subject())
                    .password("nopassword")
                    .build(), token);

        }

        throw new UsernameNotFoundException("Principal must be type Token");
    }
}
