package com.bancointernacional.plataformaBI.util;

import java.util.UUID;

public class UUIDValidator {
    public static boolean isValidUUID(String str) {
        try {
            UUID uuid = UUID.fromString(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}