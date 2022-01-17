package com.github.white.at.framework.excel.style;

import java.util.List;
import java.util.function.Supplier;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;

import cn.hutool.core.collection.ListUtil;

public class DefaultExportStyleFactories {

    private DefaultExportStyleFactories() {
    }

    public static HorizontalCellStyleStrategy createDefaultStyle() {
        WriteCellStyle headStyle = InstanceHolder.INSTANCE.headStyle();
        List<WriteCellStyle> contentStyle = InstanceHolder.INSTANCE.contentStyle();
        return new HorizontalCellStyleStrategy(headStyle, contentStyle);
    }

    public static CoordinateStyleStrategy createFillValueHandler(String excelName, boolean time, boolean rowNo) {
        CoordinateStyleStrategy css = new CoordinateStyleStrategy().writeTitle(excelName);
        if (time) {
            css.writeExportTime();
        }
        if (rowNo) {
            css.writeRowNo();
        }
        return css;
    }

    private WriteCellStyle headStyle() {
        WriteCellStyle wcs = new WriteCellStyle();
        // 颜色
        wcs.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        wcs.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 对齐方式
        wcs.setVerticalAlignment(VerticalAlignment.CENTER);
        wcs.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 字体
        WriteFont font = new WriteFont();
        font.setFontHeightInPoints((short) 14);
        font.setFontName("宋体");
        font.setBold(true);
        wcs.setWriteFont(font);
        return addBorderStyle(wcs);
    }

    private List<WriteCellStyle> contentStyle() {
        Supplier<WriteCellStyle> supplier = () -> {
            WriteCellStyle wcs = new WriteCellStyle();
            wcs.setVerticalAlignment(VerticalAlignment.CENTER);
            wcs.setHorizontalAlignment(HorizontalAlignment.LEFT);
            addBorderStyle(wcs);
            return wcs;
        };
        // 斑马线
        WriteCellStyle row1 = supplier.get();
        WriteCellStyle row2 = supplier.get();
        row2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        row2.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        return ListUtil.toList(row1, row2);
    }

    private WriteCellStyle addBorderStyle(WriteCellStyle wcs) {
        short bc = IndexedColors.BLACK.getIndex();
        wcs.setTopBorderColor(bc);
        wcs.setBottomBorderColor(bc);
        wcs.setLeftBorderColor(bc);
        wcs.setRightBorderColor(bc);
        BorderStyle bs = BorderStyle.THIN;
        wcs.setBorderTop(bs);
        wcs.setBorderBottom(bs);
        wcs.setBorderLeft(bs);
        wcs.setBorderRight(bs);
        return wcs;
    }

    private static class InstanceHolder {

        private static final DefaultExportStyleFactories INSTANCE = new DefaultExportStyleFactories();

        private InstanceHolder() {
        }
    }
}
