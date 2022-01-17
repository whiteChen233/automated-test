package com.github.white.at.framework.excel.support;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.white.at.framework.basic.AtId;
import com.github.white.at.framework.basic.Query;
import com.github.white.at.framework.excel.domain.BaseExcel;
import com.github.white.at.framework.excel.domain.WriteExcelData;
import com.github.white.at.framework.excel.style.DefaultExportStyleFactories;

public interface DefaultExcelExport<Q extends Query, D extends AtId, E extends BaseExcel> extends ExcelExport<Q> {

    @Override
    default WriteExcelData export(Q q, Pageable pageable) {
        String excelName = q.getExcelName();
        WriteExcelData excelData = new WriteExcelData(excelName)
            .registerClass(getExcelClass())
            .addStyle(DefaultExportStyleFactories.createDefaultStyle())
            .addStyle(DefaultExportStyleFactories.createFillValueHandler(excelName, true, true));
        do {
            Page<D> page = findAll(q, pageable);
            excelData.addContent(page.map(mapping()).getContent());
            pageable = page.nextPageable();
        } while (pageable.isPaged());
        return excelData;
    }

    @SuppressWarnings("unchecked")
    default Class<E> getExcelClass() {
        Type[] types = getClass().getGenericInterfaces();
        for (Type t : types) {
            ParameterizedType pt = (ParameterizedType) t;
            if (ExcelExport.class.equals(pt.getRawType())) {
                return (Class<E>) pt.getActualTypeArguments()[2];
            }
        }
        throw new UnsupportedOperationException("not implemented: " + this.getClass());
    }

    Page<D> findAll(Q q, Pageable pageable);

    Function<D, E> mapping();
}
