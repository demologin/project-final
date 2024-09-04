package com.javarush.jira.common.jwt.serializer;

import com.javarush.jira.common.jwt.Token;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@RequiredArgsConstructor
@Setter
@Slf4j
public class AccessTokenJwsStringSerializer implements Serializer<Token, String> {

    private final JWSSigner jwsSigner;
    private JWSAlgorithm jwsAlgorithm = JWSAlgorithm.HS256;

    @Override
    public String apply(Token token) {
        JWSHeader header = new JWSHeader.Builder(jwsAlgorithm)
                .keyID(token.id().toString()).
                build();

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .jwtID(token.id().toString())
                .subject(token.subject())
                .issueTime(Date.from(token.createdAt()))
                .expirationTime(Date.from(token.expiresAt()))
                .claim("authorities", token.authorities())
                .build();

        SignedJWT signedJWT = new SignedJWT(header, claimsSet);
        try {
            signedJWT.sign(jwsSigner);
            return signedJWT.serialize();
        } catch (JOSEException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }
}
