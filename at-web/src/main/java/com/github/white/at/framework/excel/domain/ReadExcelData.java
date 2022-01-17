package com.github.white.at.framework.excel.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;

@Getter
public class ReadExcelData<E extends BaseExcel> {

    private final List<Map<Integer, String>> heads;

    private final List<E> content;

    public ReadExcelData() {
        this.heads = new ArrayList<>();
        this.content = new ArrayList<>();
    }

    public void addData(E e) {
        this.content.add(e);
    }

    public void addHead(Map<Integer, String> head) {
        this.heads.add(head);
    }
}
