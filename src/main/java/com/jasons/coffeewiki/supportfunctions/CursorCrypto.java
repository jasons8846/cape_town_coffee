package com.jasons.coffeewiki.supportfunctions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class CursorCrypto {

    @Value("${encryption.secret}")
    private String secret;
    @Value("${encryption.algo}")
    private String algo;

    private static final  SecureRandom random = new SecureRandom();
    public String encrypt(String value) {

        System.out.println(secret);
        System.out.println(algo);

        try {
            byte[] iv = new byte[12];
            random.nextBytes(iv);

            GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
            Cipher cipher = Cipher.getInstance(algo);
            SecretKey key = new SecretKeySpec(secret.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, key, gcmSpec);

            byte[] cipherText = cipher.doFinal(value.getBytes());

            // Combine IV + ciphertext
            byte[] combined = new byte[iv.length + cipherText.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(cipherText, 0, combined, iv.length, cipherText.length);


            return Base64.getUrlEncoder().encodeToString(combined);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String cursor) {

        try {
            byte[] decoded = Base64.getUrlDecoder().decode(cursor);

            byte[] iv = new byte[12];
            byte[] cipherText = new byte[decoded.length - 12];

            System.arraycopy(decoded, 0, iv, 0, 12);
            System.arraycopy(decoded, 12, cipherText, 0, cipherText.length);


            GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
            Cipher cipher = Cipher.getInstance(algo);
            SecretKey key = new SecretKeySpec(secret.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, key, gcmSpec);
            return new String(cipher.doFinal(cipherText));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
