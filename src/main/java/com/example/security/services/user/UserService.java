package com.example.security.services.user;


import com.example.security.dto.user.UserRequestCreate;
import com.example.security.dto.user.UserResponse;

public interface UserService {
    public UserResponse getUser(String email);
    public void updateUser(UserResponse dto);
    public boolean checkExistAccount(String email);
    public void register(UserRequestCreate dto);
    public void lockUser(String email);
}
