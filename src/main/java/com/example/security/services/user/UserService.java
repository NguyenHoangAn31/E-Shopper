package com.example.security.services.user;


import com.example.security.dto.user.UserRequestCreate;
import com.example.security.dto.user.UserResponse;
import com.example.security.entities.User;

public interface UserService {
    public User findById(int id);
    public User findByEmail(String email);
    public void lockUser(String email);
    public void updateUser(UserResponse dto);
    public UserResponse getUser(String email);
    public int getIdUserByEmail(String email);
    public void register(UserRequestCreate dto);
    public boolean checkExistAccount(String email);
}
