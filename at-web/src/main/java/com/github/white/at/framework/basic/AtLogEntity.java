package com.github.white.at.framework.basic;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public class AtLogEntity {

    private long id;

    private String createBy;

    private LocalDateTime createTime;
}
