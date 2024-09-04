package com.javarush.jira.common.jwt.serializer;

import com.javarush.jira.common.jwt.Token;
import com.nimbusds.jose.*;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@RequiredArgsConstructor
@Setter
@Slf4j
@Component
public class RefreshTokenJweStringSerializer implements Serializer<Token, String> {

    private final JWEEncrypter jweEncrypter;

    private JWEAlgorithm jweAlgorithm = JWEAlgorithm.DIR;
    private EncryptionMethod encryptionMethod = EncryptionMethod.A128GCM;

    @Override
    public String apply(Token token) {
        JWEHeader header = new JWEHeader.Builder(jweAlgorithm, encryptionMethod)
                .keyID(token.id().toString()).
                build();

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .jwtID(token.id().toString())
                .subject(token.subject())
                .issueTime(Date.from(token.createdAt()))
                .expirationTime(Date.from(token.expiresAt()))
                .claim("authorities", token.authorities())
                .build();

        EncryptedJWT encryptedJWT = new EncryptedJWT(header, claimsSet);
        try {
            encryptedJWT.encrypt(jweEncrypter);
            return encryptedJWT.serialize();
        } catch (JOSEException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }
}
