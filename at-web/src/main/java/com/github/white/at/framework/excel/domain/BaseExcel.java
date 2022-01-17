package com.github.white.at.framework.excel.domain;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnore;

import lombok.Data;

@Data
public class BaseExcel implements Serializable {

    @ExcelIgnore
    private int excelRowIndex;
}
