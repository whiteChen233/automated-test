package com.github.white.at.api.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.white.at.api.UserService;

@Service
public class UserServiceImpl implements UserService {

//    private final UserRespository userRespository;

//    public UserServiceImpl(UserRespository userRespository) {
//        this.userRespository = userRespository;
//    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
