package com.github.white.at.common.excel.support;

import org.springframework.data.domain.Pageable;

import com.github.white.at.common.basic.Query;
import com.github.white.at.common.excel.domain.WriteExcelData;

public interface ExcelExport<Q extends Query> {

    WriteExcelData export(Q q, Pageable pageable);

}
