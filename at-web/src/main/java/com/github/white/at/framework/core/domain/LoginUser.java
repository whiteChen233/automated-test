package com.github.white.at.framework.core.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.white.at.framework.enums.AccountEnum;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginUser implements UserDetails {

    private String uuid;
    @JsonIgnore
    private String token;
    private Long userId;
    private String username;
    private String password;
    private AccountEnum status;
    private List<String> roles;
    private List<String> perms;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(roles)
            .map(i -> i.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
            .orElseGet(ArrayList::new);
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isCredentialsNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !AccountEnum.LOCKED.equals(status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return AccountEnum.AVAILABLE.equals(status);
    }
}
