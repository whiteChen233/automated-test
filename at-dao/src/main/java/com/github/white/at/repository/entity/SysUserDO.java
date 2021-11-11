package com.github.white.at.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.github.white.at.framework.base.AtEntity;

import lombok.Data;

@Data
@Entity
@Table(name = "sys_user")
public class SysUserDO extends AtEntity {

    private String username;

    private String password;

    private String nickname;

    private Integer status;

    private String level;

    private String gender;
}
