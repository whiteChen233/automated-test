package com.github.white.at.framework.core.base;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public class AtId {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
}
