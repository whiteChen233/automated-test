package com.github.white.at.api.mapper;

import org.mapstruct.Mapper;

import com.github.white.at.api.domain.SysUserVO;
import com.github.white.at.repository.entity.SysUserDO;

@Mapper
public interface SysUserMapper {

    SysUserVO toObject(SysUserDO entity);

    SysUserDO toEntity(SysUserVO dto);
}
