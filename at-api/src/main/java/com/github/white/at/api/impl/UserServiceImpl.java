package com.github.white.at.api.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.github.white.at.api.UserService;
import com.github.white.at.api.mapper.SysUserMapper;
import com.github.white.at.framework.core.domain.LoginUser;
import com.github.white.at.repository.SysUserRepository;
import com.github.white.at.repository.entity.SysUserDO;

@Service
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
            .id(u.getId())
            .username(u.getUsername())
            .build();
    }
}
