package com.github.white.at.common.excel;

import java.time.LocalDateTime;

import org.apache.tomcat.util.http.fileupload.FileItem;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.github.white.at.common.excel.linstener.UploadExcelListener;

import cn.hutool.core.util.ClassUtil;

public class ExcelUtils {

    public <E extends BaseExcel> ExcelData<E> upload(FileItem fileItem) {
        try {
            UploadExcelListener<E> listener = new UploadExcelListener<>(fileItem.getName());
            Class<?> clazz = ClassUtil.getTypeArgument(UploadExcelListener.class);
            EasyExcelFactory.read(fileItem.getInputStream(), clazz, listener).sheet().doRead();
            return listener.getExcelData();
        } catch (Exception e) {
            return null;
        }
    }

    public <E extends BaseExcel> String download(ExcelData<E> excelData) {
        String fileName = excelData.getExcelName() + "_" + LocalDateTime.now() + ExcelTypeEnum.XLSX.getValue();
        Class<?> clazz = ClassUtil.getTypeArgument(excelData.getClass());
        EasyExcelFactory.write(fileName, clazz).registerWriteHandler(excelData.getStyle()).sheet().doWrite(excelData.getCells());
        return fileName;
    }
}
