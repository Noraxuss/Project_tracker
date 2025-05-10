package project_tracker.application.domain;

public record LoginModule(String userName, String password) {
    public LoginModule {
        if (userName == null || userName.isBlank()) {
            throw new IllegalArgumentException("User name cannot be null or blank");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }
    }
}
