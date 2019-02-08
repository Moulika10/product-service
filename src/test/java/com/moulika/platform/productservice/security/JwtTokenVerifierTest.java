package com.mapp.platform.productservice.security;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.apache.commons.codec.binary.Base64;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class JwtTokenVerifierTest {

    @InjectMocks
    private JwtTokenVerifier jwtTokenVerifier;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void decodeToken() {

        DecodedJWT jwt = jwtTokenVerifier.decodeToken(" eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ijg2MzY4NjI2LWFiNjktNGJkZS1iMzZkLWVj"
                + "Zjg3YmM0ODFiYSJ9.eyJhdWQiOlsicHJvZHVjdC1zZXJ2aWNlIl0sInNjb3BlIjpbIm9wZW5pZCJdLCJpc3MiOi"
                + "JhdXRoLWxvY2FsIiwiZXhwIjoxNTM2NDA5NTU5LCJqdGkiOiI4N2U4NGIxNi1mODg1LTRjMzUtODJiZS04YzMzOGI1M"
                + "jZiYmYiLCJjbGllbnRfaWQiOiJwcm9kdWN0LXNlcnZpY2UifQ.VlI7d4NO_1l9ulOnlIGFYoESBH0VFHDRmM8yTRt8MZfBdTfjRp"
                + "z5xI9GLLIKATydJQmduWnAGksoUCuxtn7egIAATQk1NursN4VyEEkBhQWrS4XF91cLmeexzVBzveIMsEV0zcdC3tYcrFKl7BezTifi5Q6_"
                + "HYCR7UBzgAN_-WlEEPLFWjUAk7FZjLvcv8fpDO0BNpuXUIzpQE0fz0xa3GdCQNWJpSX21yVAcAdFgoUJ3kMy2-UPDWWm83J3lkIZGFBfFU4MA65"
                + "V_1B5l4k-UYvmpIsTw-VT_dT1IL3qbw1fSSWSHod95TlY5XEcsz63S58-6TsNWe4N5w0W2cy-pw");

        assertThat(jwt.getKeyId(), is(notNullValue()));
        assertThat(jwt.getKeyId(), is("86368626-ab69-4bde-b36d-ecf87bc481ba"));
    }

    @Test
    public void shouldThrowIfLessThan3Parts() {
        expectedEx.expect(JWTDecodeException.class);
        expectedEx.expectMessage("The token was expected to have 3 parts, but got 2.");
        jwtTokenVerifier.decodeToken("two.parts");
    }

    @Test
    public void shouldThrowIfMoreThan3Parts(){
        expectedEx.expect(JWTDecodeException.class);
        expectedEx.expectMessage("The token was expected to have 3 parts, but got 4.");
        jwtTokenVerifier.decodeToken("this.has.four.parts");
    }

    @Test
    public void shouldThrowIfPayloadHasInvalidJSONFormat() {
        String validJson = "{}";
        String invalidJson = "}{";
        expectedEx.expect(JWTDecodeException.class);
        expectedEx.expectMessage(String.format("The string '%s' doesn't have a valid JSON format.", invalidJson));
        customJWT(validJson, invalidJson);
    }

    @Test
    public void shouldThrowIfHeaderHasInvalidJSONFormat() {
        String validJson = "{}";
        String invalidJson = "}{";
        expectedEx.expect(JWTDecodeException.class);
        expectedEx.expectMessage(String.format("The string '%s' doesn't have a valid JSON format.", invalidJson));
        customJWT(invalidJson, validJson);
    }

    private void customJWT(String jsonHeader, String jsonPayload) {
        String header = Base64.encodeBase64URLSafeString(jsonHeader.getBytes(StandardCharsets.UTF_8));
        String body = Base64.encodeBase64URLSafeString(jsonPayload.getBytes(StandardCharsets.UTF_8));
        jwtTokenVerifier.decodeToken(String.format("%s.%s.%s", header, body, jsonPayload));
    }

    @Test
    public void shouldPassNoneVerification() {
        Algorithm algorithm = Algorithm.none();
        String jwt = "eyJhbGciOiJub25lIiwiY3R5IjoiSldUIn0.eyJpc3MiOiJhdXRoMCJ9.";
        algorithm.verify(jwtTokenVerifier.decodeToken(jwt));
    }

    @Test
    public void shouldFailNoneVerificationWhenTokenHasTwoParts() {
        expectedEx.expect(JWTDecodeException.class);
        expectedEx.expectMessage("The token was expected to have 3 parts, but got 2.");
        String jwt = "eyJhbGciOiJub25lIiwiY3R5IjoiSldUIn0.eyJpc3MiOiJhdXRoMCJ9";
        Algorithm algorithm = Algorithm.none();
        algorithm.verify(jwtTokenVerifier.decodeToken(jwt));
    }

    @Test
    public void shouldFailNoneVerificationWhenSignatureIsPresent() {
        expectedEx.expect(SignatureVerificationException.class);
        expectedEx.expectMessage("The Token's Signature resulted invalid when verified using the Algorithm: none");
        String jwt = "eyJhbGciOiJub25lIiwiY3R5IjoiSldUIn0.eyJpc3MiOiJhdXRoMCJ9.Ox-WRXRaGAuWt2KfPvWiGcCrPqZtbp_4OnQzZXaTfss";
        Algorithm algorithm = Algorithm.none();
        algorithm.verify(jwtTokenVerifier.decodeToken(jwt));
    }

    @Test
    public void verifyWhenSignatureIsInvalid()
        throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        expectedEx.expectMessage("The Token's Signature resulted invalid when verified using the Algorithm: SHA256withRSA");
        String token = " eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ijg2MzY4NjI2LWFiNjktNGJkZS1iMzZkLWVj"
             + "Zjg3YmM0ODFiYSJ9.eyJhdWQiOlsicHJvZHVjdC1zZXJ2aWNlIl0sInNjb3BlIjpbIm9wZW5pZCJdLCJpc3MiOi"
             + "JhdXRoLWxvY2FsIiwiZXhwIjoxNTM2NDA5NTU5LCJqdGkiOiI4N2U4NGIxNi1mODg1LTRjMzUtODJiZS04YzMzOGI1M"
             + "jZiYmYiLCJjbGllbnRfaWQiOiJwcm9kdWN0LXNlcnZpY2UifQ.VlI7d4NO_1l9ulOnlIGFYoESBH0VFHDRmM8yTRt8MZfBdTfjRp"
             + "z5xI9GLLIKATydJQmduWnAGksoUCuxtn7egIAATQk1NursN4VyEEkBhQWrS4XF91cLmeexzVBzveIMsEV0zcdC3tYcrFKl7BezTifi5Q6_"
             + "HYCR7UBzgAN_-WlEEPLFWjUAk7FZjLvcv8fpDO0BNpuXUIzpQE0fz0xa3GdCQNWJpSX21yVAcAdFgoUJ3kMy2-UPDWWm83J3lkIZGFBfFU4MA65"
             + "V_1B5l4k-UYvmpIsTw-VT_dT1IL3qbw1fSSWSHod95TlY5XEcsz63S58-6TsNWe4N5w0W2cy-pw";
       when(jwtTokenVerifier.verify(token)).thenReturn(null);
        }
}
