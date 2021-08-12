package com.guthub.white.respository.entity;

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

    private Integer status;

    private String level;

    private String nickname;

    private String gender;
}
