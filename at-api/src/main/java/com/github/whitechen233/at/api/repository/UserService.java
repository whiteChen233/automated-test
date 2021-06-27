package com.github.whitechen233.at.api.repository;


import com.github.whitechen233.at.api.domain.SysUser;

public interface UserService {

    String getUserName(Long id);

    SysUser addUser(SysUser user);
}
