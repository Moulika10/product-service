package com.mapp.platform.productservice.security;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import org.assertj.core.api.Assertions;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class KeyUtilsTest {

  @Rule
  public ExpectedException expectedEx = ExpectedException.none();

  @Test
  public void readPublicKeyFromFileWhenNull()
      throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
    expectedEx.expect(NullPointerException.class);
    when(KeyUtils.readPublicKeyFromFile( null)).thenReturn(null);
  }

  @Test
  public void readFromFile() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    InputStream in = KeyUtils.class.getClassLoader().getResourceAsStream("rsa-publicKey.pem");
    PemReader reader = new PemReader(new InputStreamReader(in));
    PemObject pemObject = reader.readPemObject();
    byte[] content = pemObject.getContent();
    reader.close();
    PublicKey publicKey;
    KeyFactory kf = KeyFactory.getInstance("RSA");
    EncodedKeySpec keySpec = new X509EncodedKeySpec(content);
    publicKey = kf.generatePublic(keySpec);
    Assertions.assertThat(KeyUtils.readPublicKeyFromFile( "RSA")).isEqualTo(publicKey);
  }

  @Test
  public void getPublicKeyWithInvalidAlgorithm()
      throws InvalidKeySpecException, NoSuchAlgorithmException {
    expectedEx.expectMessage("No Such Algorithm exists");
    Assertions.assertThat(KeyUtils.getPublicKey(new byte[0],"abcde")).isEqualTo(null);
  }

  @Test
  public void getPublicKeyWithInvalidKeyBytes()
      throws InvalidKeySpecException, NoSuchAlgorithmException {
    expectedEx.expectMessage("Could not construct Public Key");
    Assertions.assertThat(KeyUtils.getPublicKey(new byte[0],"RSA")).isEqualTo(null);
  }

}
