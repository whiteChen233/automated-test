package com.github.white.at.common.excel.linstener;

import java.util.Map;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.github.white.at.common.excel.domain.BaseExcel;
import com.github.white.at.common.excel.domain.ReadExcelData;
import com.github.white.at.common.utils.JSON;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
public class UploadExcelListener<E extends BaseExcel> extends AnalysisEventListener<E> {

    private final ReadExcelData<E> excelData;

    public UploadExcelListener() {
        this.excelData = new ReadExcelData<>();
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        this.excelData.addHead(headMap);
        log.debug("get a head: {}", () -> JSON.INSTANCE.toString(headMap));
    }

    @Override
    public void invoke(E e, AnalysisContext analysisContext) {
        e.setExcelRowIndex(analysisContext.readRowHolder().getRowIndex() + 1);
        this.excelData.addData(e);
        log.debug("get a data: {}", () -> JSON.INSTANCE.toString(e));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("Excel analysis is over. size: {}", this.excelData.getContent().size());
    }
}
