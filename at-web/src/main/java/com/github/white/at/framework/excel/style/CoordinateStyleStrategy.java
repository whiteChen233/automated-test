package com.github.white.at.framework.excel.style;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;

import cn.hutool.core.map.MapUtil;

public class CoordinateStyleStrategy extends AbstractCellStyleStrategy {

    private final Map<String, SingleCellStyle> cellStyleMap;

    private boolean writeRowNo;

    public CoordinateStyleStrategy() {
        this.cellStyleMap = MapUtil.newHashMap();
        this.writeRowNo = false;
    }

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        // 这里要把 WriteCellStyle 的样式清空，不然后面的拦截器 FillStyleCellWriteHandler 默认会将其设置到 cell 中，从而与预期的样式一致
        context.getFirstCellData().setWriteCellStyle(null);
        super.afterCellDispose(context);
    }

    @Override
    protected void setHeadCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
        String key = getKey(relativeRowIndex, cell.getColumnIndex());
        Optional.ofNullable(cellStyleMap.get(key)).ifPresent(scs -> {
            Optional.ofNullable(scs.getValue()).ifPresent(cell::setCellValue);
            Optional.ofNullable(scs.getStyle()).ifPresent(cell::setCellStyle);
        });
    }

    @Override
    protected void setContentCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
        if (writeRowNo && cell.getColumnIndex() == 0) {
            cell.setCellValue(relativeRowIndex + NumberUtils.DOUBLE_ONE);
        }
    }

    public CoordinateStyleStrategy addCellStyle(SingleCellStyle style) {
        this.cellStyleMap.put(getKey(style.getRow(), style.getColumn()), style);
        return this;
    }

    public CoordinateStyleStrategy writeTitle(String excelName) {
        return this.addCellStyle(new SingleCellStyle(0, 0, excelName));
    }

    public void writeExportTime() {
        this.addCellStyle(new SingleCellStyle(1, 0, "导出时间：" + LocalDateTime.now()));
    }

    public void writeRowNo() {
        this.writeRowNo = true;
    }

    private String getKey(int row, int column) {
        return StringUtils.joinWith("-", row, column);
    }
}
