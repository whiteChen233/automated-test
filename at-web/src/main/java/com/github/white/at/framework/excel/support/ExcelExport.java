package com.github.white.at.framework.excel.support;

import org.springframework.data.domain.Pageable;

import com.github.white.at.framework.basic.Query;
import com.github.white.at.framework.excel.domain.WriteExcelData;

public interface ExcelExport<Q extends Query> {

    WriteExcelData export(Q q, Pageable pageable);

}
