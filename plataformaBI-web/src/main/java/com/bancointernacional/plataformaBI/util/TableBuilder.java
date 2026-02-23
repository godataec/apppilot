package com.bancointernacional.plataformaBI.util;

// TableBuilder.java
import com.bancointernacional.plataformaBI.domain.model.period.UserAnswer;
import com.bancointernacional.plataformaBI.domain.model.report.table.TableColumn;
import com.bancointernacional.plataformaBI.domain.model.report.table.TableView;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TableBuilder {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Pattern ROW_KEY = Pattern.compile("^row_(\\d+)$");

    public static Map<String, TableView> buildTables(List<UserAnswer> answers) {
        Map<String, List<UserAnswer>> byDoc = answers.stream()
                .filter(a -> "TABLE".equalsIgnoreCase(a.getAnswerType()))
                .collect(Collectors.groupingBy(UserAnswer::getDocumentId, LinkedHashMap::new, Collectors.toList()));

        Map<String, TableView> tables = new LinkedHashMap<>();
        for (Map.Entry<String, List<UserAnswer>> entry : byDoc.entrySet()) {
            String docId = entry.getKey();
            List<UserAnswer> docUserAnswers = entry.getValue();
            List<TableColumn> columns = new ArrayList<>();
            Set<String> allRowKeys = new HashSet<>();
            for (UserAnswer a : docUserAnswers) {
                try {
                    JsonNode node = MAPPER.readTree(a.getAnswerText());
                    int colIndex = node.path("_columnIndex").asInt();

                    TableColumn col = new TableColumn(colIndex);

                    Iterator<String> it = node.fieldNames();
                    while (it.hasNext()) {
                        String key = it.next();
                        if (ROW_KEY.matcher(key).matches()) {
                            col.rows.put(key, optText(node.get(key)));
                            allRowKeys.add(key);
                        }
                    }
                    columns.add(col);
                } catch (Exception ignored) {}
            }
            columns.sort(Comparator.comparingInt(c -> c.columnIndex));
            List<String> orderedRowKeys = allRowKeys.stream()
                    .sorted(Comparator.comparingInt(TableBuilder::rowNumber))
                    .collect(Collectors.toList());
            TableView tv = new TableView(docId);
            tv.columns.addAll(columns);
            tv.rowKeys.addAll(orderedRowKeys);
            tables.put(docId, tv);
        }
        return tables;
    }

    private static int rowNumber(String rowKey) {
        Matcher m = ROW_KEY.matcher(rowKey);
        return m.matches() ? Integer.parseInt(m.group(1)) : Integer.MAX_VALUE;
    }

    private static String optText(JsonNode node) {
        return (node == null || node.isNull()) ? "" : node.asText("");
    }
}