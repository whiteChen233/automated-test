package com.guthub.white.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guthub.white.respository.entity.SysUserDO;

public interface UserRespository extends JpaRepository<SysUserDO, Long> {

}
