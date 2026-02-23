package com.bancointernacional.plataformaBI.domain.model.report.table;

import java.util.ArrayList;
import java.util.List;

public class TableView {
    public String id;
    public List<String> rowKeys = new ArrayList<>();
    public List<TableColumn> columns = new ArrayList<>();

    public TableView(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<String> rowKeys() {
        return rowKeys;
    }

    public List<TableColumn> columns() {
        return columns;
    }
}