package com.javarush.jira.common.jwt;

import com.javarush.jira.login.AuthUser;
import com.javarush.jira.login.User;

public class TokenUser extends AuthUser {

    private final Token token;

    public TokenUser(User user, Token token) {
        super(user);
        this.token = token;
    }
}
