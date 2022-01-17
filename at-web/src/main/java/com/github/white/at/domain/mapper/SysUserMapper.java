package com.github.white.at.domain.mapper;

import org.mapstruct.Mapper;

import com.github.white.at.domain.entity.SysUserDO;
import com.github.white.at.domain.view.SysUserVO;

@Mapper
public interface SysUserMapper {

    SysUserVO toObject(SysUserDO entity);

    SysUserDO toEntity(SysUserVO dto);
}
