package service;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Component
public class CryptService {

    private static final String CHARSET_NAME = "ISO-8859-1";
    private static final String SECRET_KEY = "PBKDF2WithHmacSHA1";
    private static final String ENCRYPTION_ALG = "AES";
    private static final String CYPHER = "AES/CBC/PKCS5Padding";

    private static final String KEY_PHRASE = "myKeyPhrase";
    private static final String INITIALIZATION_VECTOR = "1236282689012316";
    private static final String SALT = "1249387982347";
    private static final int ITERATIONS = 10000;
    private static final int KEY_SIZE = 256;


    public String crypt(String plainText) {

        try {
            SecretKeySpec secret = getSecretKey();

            byte[] ivBytes = INITIALIZATION_VECTOR.getBytes(CHARSET_NAME);

            Cipher cipher = Cipher.getInstance(CYPHER);
            cipher.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(ivBytes));

            byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes(CHARSET_NAME));

            return new String(Base64.encodeBase64(encryptedTextBytes));

        } catch (Exception e) {
            System.out.println("Error encrypting");
        }

        return null;
    }

    public String decrypt(String encryptedText) {

        try {
            SecretKeySpec secret = getSecretKey();

            byte[] ivBytes = INITIALIZATION_VECTOR.getBytes(CHARSET_NAME);
            byte[] encryptedTextBytes = Base64.decodeBase64(encryptedText.getBytes());

            Cipher cipher = Cipher.getInstance(CYPHER);
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));

            byte[] decryptedTextBytes = cipher.doFinal(encryptedTextBytes);

            return new String(decryptedTextBytes);
        } catch (Exception e) {
            System.out.println("Error decrypting");
        }
        return null;
    }

    public SecretKeySpec getSecretKey() {
        SecretKeySpec secret = null;

        try {
            byte[] saltBytes = SALT.getBytes(CHARSET_NAME);

            SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY);
            PBEKeySpec spec = new PBEKeySpec(
                    KEY_PHRASE.toCharArray(),
                    saltBytes,
                    ITERATIONS,
                    KEY_SIZE
            );

            SecretKey secretKey = factory.generateSecret(spec);
            secret = new SecretKeySpec(secretKey.getEncoded(), ENCRYPTION_ALG);

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Specified algorithm does not exist");
        } catch (InvalidKeySpecException e) {
            System.out.println("Invalid key specification for generating secret key");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Encoding specified is not supported");
        }
        return secret;
    }
}
