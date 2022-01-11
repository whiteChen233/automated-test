package com.github.white.at.framework.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsRepository {

    private final Map<String, UserDetails> users = new HashMap<>();

    public void createUser(UserDetails userDetails) {
        this.users.putIfAbsent(userDetails.getUsername(), userDetails);
    }

    public void updateUser(UserDetails userDetails) {
        this.users.put(userDetails.getUsername(), userDetails);
    }

    public void deleteUser(String username) {
        this.users.remove(username);
    }

    public boolean userExists(String username) {
        return this.users.containsKey(username);
    }

    public UserDetails loadUserByUsername(String username) {
        return this.users.get(username);
    }
}
