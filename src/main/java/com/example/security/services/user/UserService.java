package com.example.security.services.user;

import com.example.security.dto.UserCreateDto;

public interface UserService {
    public boolean checkExistAccount(String email);
    public void register(UserCreateDto dto);
    public void lockUser(String email);
}
