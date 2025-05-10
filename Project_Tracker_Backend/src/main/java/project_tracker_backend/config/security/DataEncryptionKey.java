package project_tracker_backend.config.security;

import java.security.SecureRandom;
import java.util.Base64;

public class DataEncryptionKey {

    /**
     * Generates a 256-bit (32-byte) encryption key for data encryption.
     * @return Base64 encoded DEK
     */
    public static String generateDEK() {
        byte[] key = new byte[32]; // 256-bit key
        new SecureRandom().nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
