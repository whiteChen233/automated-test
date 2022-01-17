package com.github.white.at.framework.excel;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileItem;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.github.white.at.framework.excel.domain.BaseExcel;
import com.github.white.at.framework.excel.domain.ReadExcelData;
import com.github.white.at.framework.excel.domain.WriteExcelData;
import com.github.white.at.framework.excel.linstener.UploadExcelListener;

public class ExcelUtils {

    public <E extends BaseExcel> ReadExcelData<E> upload(FileItem fileItem, Class<E> clazz) {
        try {
            UploadExcelListener<E> listener = new UploadExcelListener<>();
            EasyExcelFactory.read(fileItem.getInputStream(), clazz, listener).sheet().doRead();
            return listener.getExcelData();
        } catch (Exception e) {
            return null;
        }
    }

    public String download(WriteExcelData excelData) {
        String fileName = excelData.getExcelName() + "_" + LocalDateTime.now() + ExcelTypeEnum.XLSX.getValue();
        ExcelWriterBuilder builder = EasyExcelFactory.write(fileName);
        List<List<String>> heads = excelData.getHeads();
        if (heads.isEmpty()) {
            builder.head(excelData.getExcelClass());
        } else {
            builder.head(heads);
        }
        excelData.getStyles().forEach(builder::registerWriteHandler);
        builder.sheet().doWrite(excelData.getContent());
        return fileName;
    }
}
