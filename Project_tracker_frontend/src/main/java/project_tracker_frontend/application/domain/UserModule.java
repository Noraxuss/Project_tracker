package project_tracker_frontend.application.domain;

public record UserModule(String userName, String password, String email) {
    public UserModule {
        if (userName == null || userName.isBlank()) {
            throw new IllegalArgumentException("User name cannot be null or blank");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
    }
}
