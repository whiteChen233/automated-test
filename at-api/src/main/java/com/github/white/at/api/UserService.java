package com.github.white.at.api;


import com.github.white.at.api.domain.UserDTO;

public interface UserService {

    String getUserName(Long id);

    UserDTO addUser(UserDTO user);
}
