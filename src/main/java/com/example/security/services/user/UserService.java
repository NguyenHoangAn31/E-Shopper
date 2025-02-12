package com.example.security.services.user;

import com.example.security.dto.user.UserRequestCreate;

public interface UserService {
    public boolean checkExistAccount(String email);
    public void register(UserRequestCreate dto);
    public void lockUser(String email);
}
