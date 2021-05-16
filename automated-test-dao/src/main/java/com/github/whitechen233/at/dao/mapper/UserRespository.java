package com.github.whitechen233.at.dao.mapper;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.whitechen233.at.dao.dataobject.UserDO;
import com.github.whitechen233.at.demos.web.User;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
public interface UserRespository extends JpaRepository<User, Long> {

}
