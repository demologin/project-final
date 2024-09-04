package com.javarush.jira.common.jwt.config;

import com.nimbusds.jose.JWEEncrypter;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;

@Configuration
public class JwtConfig {

    @Bean
    public JWEEncrypter defaultJWEEncrypter(@Value("${jwt.refresh-token-key}") String refreshTokenKey) throws ParseException, KeyLengthException {
        return new DirectEncrypter(OctetSequenceKey.parse(refreshTokenKey));
    }

    @Bean
    public JWSSigner defaultJWSSigner(@Value("${jwt.access-token-key}") String accessTokenKey) throws ParseException, KeyLengthException {
        return new MACSigner(OctetSequenceKey.parse(accessTokenKey));
    }
}
