package com.github.white.at.framework.base;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Data
@MappedSuperclass
public class AtId {

    @Id
    @SequenceGenerator(name="business_seq", sequenceName="business_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "business_seq")
    private long id;
}
