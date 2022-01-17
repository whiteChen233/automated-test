package com.github.white.at.framework.excel.domain;

import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.util.Assert;

import lombok.Data;

@Data
public class SingleCellStyle {

    private final int row;

    private final int column;

    private String value;

    private CellStyle style;

    private SingleCellStyle(int row, int column) {
        Assert.isTrue(row >= 0, "row must be positive");
        Assert.isTrue(column >= 0, "column must be positive");
        this.row = row;
        this.column = column;
    }

    public SingleCellStyle(int row, int column, String value) {
        this(row, column);
        Assert.notNull(value, "value must not be null");
        this.value = value;
    }

    public SingleCellStyle(int row, int column, CellStyle style) {
        this(row, column);
        Assert.notNull(style, "style must not be null");
        this.style = style;
    }

    public SingleCellStyle(int row, int column, String value, CellStyle style) {
        this(row, column);
        Assert.notNull(value, "value must not be null");
        Assert.notNull(style, "style must not be null");
        this.value = value;
        this.style = style;
    }
}
