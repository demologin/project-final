package com.javarush.jira.common.jwt.deserializer;

import com.javarush.jira.common.jwt.Token;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Setter
@Slf4j
@Component
public class AccessTokenJwsStringDeserializer implements Deserializer<String, Token> {

    private JWSVerifier verifier;

    @Override
    public Token apply(String s) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(s);
            if (signedJWT.verify(verifier)) {
                JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
                return new Token(UUID.fromString(claimsSet.getJWTID()), claimsSet.getSubject(),
                        claimsSet.getStringListClaim("authorities"),
                        claimsSet.getIssueTime().toInstant(),
                        claimsSet.getExpirationTime().toInstant());
            }
        } catch (JOSEException | ParseException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
