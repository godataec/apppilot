package com.bancointernacional.plataformaBI.domain.model.report.table;

import java.util.LinkedHashMap;
import java.util.Map;

public class TableColumn {
    public int columnIndex;
    public Map<String, String> rows = new LinkedHashMap<>();

    public TableColumn(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public Map<String, String> getRows() {
        return rows;
    }

    public String getRowValue(String rowKey) {
        return rows.getOrDefault(rowKey, "");
    }

    public String getValue(String rowKey) {
        return rows.getOrDefault(rowKey, "");
    }
}