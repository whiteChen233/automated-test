package com.github.white.at.api.mapper;

import org.mapstruct.Mapper;

import com.github.white.at.api.domain.SysUserDTO;
import com.github.white.at.repository.entity.SysUserDO;

@Mapper
public interface SysUserMapper {

    SysUserDTO toObject(SysUserDO entity);

    SysUserDO toEntity(SysUserDTO dto);
}
