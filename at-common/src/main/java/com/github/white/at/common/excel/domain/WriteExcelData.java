package com.github.white.at.common.excel.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.alibaba.excel.write.handler.CellWriteHandler;

import lombok.Getter;

@Getter
public class WriteExcelData {

    private final String excelName;

    private final List<List<String>> heads;

    private final List<Object> content;

    private final List<CellWriteHandler> styles;

    private Class<? extends BaseExcel> excelClass;

    public WriteExcelData(String excelName) {
        this.excelName = excelName;
        this.styles = new ArrayList<>();
        this.heads = new ArrayList<>();
        this.content = new ArrayList<>();
    }

    public <E extends BaseExcel> WriteExcelData registerClass(Class<E> excelClass) {
        this.excelClass = excelClass;
        return this;
    }

    public WriteExcelData addContent(Collection<?> e) {
        this.content.addAll(e);
        return this;
    }

    public WriteExcelData addStyle(CellWriteHandler handler) {
        this.styles.add(handler);
        return this;
    }
}
