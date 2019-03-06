//package com.moulika.platform.productservice.security;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.auth0.jwt.interfaces.Verification;
//import java.io.IOException;
//import java.security.NoSuchAlgorithmException;
//import java.security.interfaces.RSAKey;
//import java.security.interfaces.RSAPublicKey;
//import java.security.spec.InvalidKeySpecException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//@Component
//class JwtTokenVerifier {
//
//    private RSAKey key;
//    private static final Logger logger = LoggerFactory.getLogger(JwtTokenVerifier.class);
//
//    DecodedJWT verify(String token)
//        throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
//        if (key == null) {
//            key = (RSAKey) KeyUtils.readPublicKeyFromFile( "RSA");
//             logger.debug("loaded from file" + key);
//        }
//        Verification verification = JWT.require(Algorithm.RSA256((RSAPublicKey) key, null));
//        return verification.build().verify(token);
//    }
//
//    DecodedJWT decodeToken(String authToken) {
//        return JWT.decode(authToken);
//    }
//}
