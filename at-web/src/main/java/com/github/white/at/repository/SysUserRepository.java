package com.github.white.at.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.github.white.at.model.domain.SysUserDO;

public interface SysUserRepository extends JpaRepository<SysUserDO, Long>, JpaSpecificationExecutor<SysUserDO> {

    Optional<SysUserDO> findOneByUsername(String s);
}
