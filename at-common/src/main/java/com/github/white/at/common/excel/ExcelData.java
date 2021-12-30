package com.github.white.at.common.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.write.style.DefaultStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;

import lombok.Getter;

@Getter
public class ExcelData<E extends BaseExcel> {

    private final String excelName;

    private final HorizontalCellStyleStrategy style;

    private final List<Map<Integer, String>> heads;

    private final List<E> cells;

    public ExcelData(String excelName) {
        this.excelName = excelName;
        this.style = new DefaultStyle();
        this.heads = new ArrayList<>();
        this.cells = new ArrayList<>();
    }

    public ExcelData(String excelName, HorizontalCellStyleStrategy style) {
        this.excelName = excelName;
        this.style = style;
        this.heads = new ArrayList<>();
        this.cells = new ArrayList<>();
    }

    public void addHead(Map<Integer, String> m) {
        this.heads.add(m);
    }

    public void addCell(E e) {
        this.cells.add(e);
    }
}
