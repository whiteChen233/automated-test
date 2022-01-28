package com.github.white.at.model.mapper;

import org.mapstruct.Mapper;

import com.github.white.at.model.domain.SysUserDO;
import com.github.white.at.model.view.SysUserVO;

@Mapper
public interface SysUserMapper {

    SysUserVO toObject(SysUserDO entity);

    SysUserDO toEntity(SysUserVO dto);
}
