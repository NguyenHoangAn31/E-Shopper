package com.example.security.services.passwordresettoken;

public interface PasswordResetTokenService {
    public void sendResetPasswordEmail(String email);
    public boolean resetPassword(String token, String newPassword);
}
