package project_tracker_backend.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Configuration
public class KeyEncryptionKey {

    private static final int SALT_LENGTH = 16;
    private static final int HASH_LENGTH = 32;
    private final Argon2Config argon2Config;

    public KeyEncryptionKey(Argon2Config argon2Config) {
        this.argon2Config = argon2Config;
    }

    /**
     * Generates a Key Encryption Key (KEK) using Argon2 hashing algorithm.
     *
     * @param password The password to be used for generating the KEK.
     * @return The generated KEK as a string.
     */

    public String generateKEK(String password) {
        Argon2PasswordEncoder encoder = getArgon2PasswordEncoder();
        return encoder.encode(password);
    }

    public Argon2PasswordEncoder getArgon2PasswordEncoder() {
        return new Argon2PasswordEncoder(
                SALT_LENGTH,
                HASH_LENGTH,
                argon2Config.getParallelism(),
                argon2Config.getMemoryUsageForEncryption(),
                argon2Config.getIterationAmount()
        );
    }
}
