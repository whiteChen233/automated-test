package com.github.white.at.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.github.white.at.repository.entity.SysUserDO;

public interface SysUserRepository extends JpaRepository<SysUserDO, Long>, JpaSpecificationExecutor<SysUserDO> {

}
