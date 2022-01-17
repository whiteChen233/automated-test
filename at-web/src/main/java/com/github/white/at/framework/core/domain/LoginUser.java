package com.github.white.at.framework.core.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.github.white.at.framework.enums.AccountEnum;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginUser implements UserDetails, CredentialsContainer {

    private Long userId;
    private String username;
    private String password;
    private Integer status;
    private List<String> roles;
    private List<String> perms;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(roles)
            .map(i -> i.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
            .orElseGet(ArrayList::new);
    }

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
        return AccountEnum.LOCKED.getStatus() == status;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return AccountEnum.AVAILABLE.getStatus() == status;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}
