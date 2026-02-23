package com.bancointernacional.plataformaBI.util;

import com.bancointernacional.plataformaBI.domain.report.bbb.AppendixForm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SerializationUtil {

    @SuppressWarnings("unchecked")
    public static Map<String, Object> convertToMap(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
        Map<String, Object> result = objectMapper.convertValue(obj, Map.class);
        if (obj instanceof AppendixForm) {
            AppendixForm form = (AppendixForm) obj;
            if (form.getTables() != null) {
                result.put("tables", form.getTables());
            }
        }
        return result;
    }

    public static String sortJsonById(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode originalNode = (ObjectNode) mapper.readTree(json);
        List<Map.Entry<String, JsonNode>> entries = new ArrayList<>();
        originalNode.fields().forEachRemaining(entries::add);
        entries.sort(Comparator.comparing(e -> e.getValue().get("Id").asText()));
        ObjectNode sortedNode = mapper.createObjectNode();
        for (Map.Entry<String, JsonNode> entry : entries) {
            sortedNode.set(entry.getKey(), entry.getValue());
        }
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(sortedNode);
    }

}
