///**package com.moulika.platform.productservice.security;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.security.KeyFactory;
//import java.security.NoSuchAlgorithmException;
//import java.security.PublicKey;
//import java.security.spec.EncodedKeySpec;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.X509EncodedKeySpec;
//import org.bouncycastle.util.io.pem.PemObject;
//import org.bouncycastle.util.io.pem.PemReader;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//@Component
//class KeyUtils {
//
//  private static final Logger logger = LoggerFactory.getLogger(KeyUtils.class);
//
//  private KeyUtils() {
//    //there is nothing to do here.
//  }
//
//  static PublicKey readPublicKeyFromFile(String algorithm)
//      throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
//    byte[] bytes = KeyUtils.parsePEMFile();
//    return KeyUtils.getPublicKey(bytes, algorithm);
//  }
//
//  private static byte[] parsePEMFile() throws IOException {
//    InputStream in = KeyUtils.class.getClassLoader().getResourceAsStream("rsa-publicKey.pem");
//    try(PemReader reader = new PemReader(new InputStreamReader(in))){
//      PemObject pemObject = reader.readPemObject();
//      byte[] content = pemObject.getContent();
//      reader.close();
//      return content;
//    }catch (IOException e) {
//      logger.error("error", e);
//      throw new IOException("No Such file or Directory Exists");
//    }
//  }
//
//  static PublicKey getPublicKey(byte[] keyBytes, String algorithm)
//      throws NoSuchAlgorithmException, InvalidKeySpecException {
//    PublicKey publicKey;
//    try {
//      KeyFactory kf = KeyFactory.getInstance(algorithm);
//      EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
//      publicKey = kf.generatePublic(keySpec);
//    } catch (NoSuchAlgorithmException e) {
//      logger.error("Could not reconstruct the public key, the given algorithm could not be found.",e);
//      throw new NoSuchAlgorithmException("No Such Algorithm exists");
//    } catch (InvalidKeySpecException e) {
//      logger.error("Could not reconstruct the public key",e);
//      throw new InvalidKeySpecException("Could not construct Public Key");
//    }
//    return publicKey;
//  }
//}
////