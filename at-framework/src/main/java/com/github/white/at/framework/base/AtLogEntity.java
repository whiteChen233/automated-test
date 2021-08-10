package com.github.white.at.framework.base;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public class AtLogEntity {

    private Long id;

    private String createBy;

    private LocalDateTime createTime;
}
