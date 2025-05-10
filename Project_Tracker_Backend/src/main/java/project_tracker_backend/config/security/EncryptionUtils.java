package project_tracker_backend.config.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtils {

    private static final String AES_ALGORITHM = "AES";

    /**
     * Encrypts the DEK using the KEK.
     * @param dek  The Data Encryption Key (DEK)
     * @param kek  The Key Encryption Key (KEK)
     * @return The encrypted DEK as a Base64 string
     */
    public static String encryptDEK(String dek, String kek) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(kek.getBytes(), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(dek.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts the encrypted DEK using the KEK.
     * @param encryptedDEK The encrypted DEK stored in the database
     * @param kek          The Key Encryption Key (KEK)
     * @return The original DEK
     */
    public static String decryptDEK(String encryptedDEK, String kek) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(kek.getBytes(), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedDEK));
        return new String(decryptedBytes);
    }
}
