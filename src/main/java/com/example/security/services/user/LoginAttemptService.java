package com.example.security.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS = 5;
    private static final long LOCK_TIME_DURATION = TimeUnit.MINUTES.toMillis(1); // 1 phút
    private static final String LOGIN_ATTEMPT_PREFIX = "login_attempt:";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // Kiểm tra xem tài khoản có bị khóa không
    public boolean isBlocked(String email) {
        String key = LOGIN_ATTEMPT_PREFIX + email;
        String lockedUntil = redisTemplate.opsForValue().get(key + ":lockedUntil");

        if (lockedUntil != null && Long.parseLong(lockedUntil) > System.currentTimeMillis()) {
            return true; // Tài khoản bị khóa
        }

        // Nếu hết thời gian khóa thì xóa thông tin
        redisTemplate.delete(key);
        return false; // Tài khoản không bị khóa
    }

    // Gọi khi đăng nhập thành công
    // public void loginSucceeded(String email) {
    //     String key = LOGIN_ATTEMPT_PREFIX + email;
    //     redisTemplate.delete(key); // Xóa thông tin khi đăng nhập thành công
    // }

    // Gọi khi đăng nhập thất bại
    public void loginFailed(String email) {
        String key = LOGIN_ATTEMPT_PREFIX + email;
        String attemptKey = key + ":attempts";
        String attempts = redisTemplate.opsForValue().get(attemptKey);

        int currentAttempts = attempts == null ? 0 : Integer.parseInt(attempts);
        currentAttempts++;

        if (currentAttempts >= MAX_ATTEMPTS) {
            // Nếu đạt 5 lần sai, khóa tài khoản trong 1 phút
            redisTemplate.opsForValue().set(key + ":lockedUntil", String.valueOf(System.currentTimeMillis() + LOCK_TIME_DURATION));
            // Reset số lần nhập sai sau khi khóa tài khoản
            redisTemplate.delete(attemptKey);
        } else {
            // Nếu chưa đủ 5 lần sai, cập nhật số lần sai và hết hạn sau 1 giờ
            redisTemplate.opsForValue().set(attemptKey, String.valueOf(currentAttempts), 1, TimeUnit.HOURS);
        }
    }
}
