package com.bancointernacional.plataformaBI.enums;

public enum AuditAction {
    CREATE("CREATE"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    private final String value;

    AuditAction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}