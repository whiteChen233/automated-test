package com.github.white.at.service.impl;

import org.springframework.security.core.userdetails.UserDetails;

import com.github.white.at.model.domain.SysUserDO;
import com.github.white.at.model.mapper.SysUserMapper;
import com.github.white.at.framework.web.LoginUser;
import com.github.white.at.repository.SysUserRepository;
import com.github.white.at.service.UserService;

// @Service
public class UserServiceImpl implements UserService {

    private final SysUserRepository repository;

    private final SysUserMapper mapper;

    public UserServiceImpl(SysUserRepository repository, SysUserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String s) {
        SysUserDO u = repository.findOneByUsername(s).orElseGet(SysUserDO::new);
        return LoginUser.builder()
            .userId(u.getId())
            .username(u.getUsername())
            .build();
    }
}
