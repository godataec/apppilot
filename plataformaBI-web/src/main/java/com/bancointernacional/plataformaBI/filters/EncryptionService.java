package com.bancointernacional.plataformaBI.filters;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.spec.KeySpec;
import java.util.Base64;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EncryptionService {

    private static final String ALGORITHM = "AES";
    private SecretKey secretKeySpec = null;
    private static final int KEY_LENGTH = 256;
    private static final int ITERATION_COUNT = 65536;

    private String salt = "PlataformaBI_Salt";



    public EncryptionService(@Value("${encryption.key}") String base64Key) {
        if (base64Key == null || base64Key.trim().isEmpty()) {
            log.error("La propiedad 'encryption.key' no está configurada o está vacía");
            throw new IllegalArgumentException("La propiedad 'encryption.key' no está configurada o está vacía");
        }

        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(base64Key.trim().toCharArray(), salt.getBytes(), ITERATION_COUNT, KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            this.secretKeySpec = new SecretKeySpec(tmp.getEncoded(), ALGORITHM);
            log.info("EncryptionService inicializado correctamente");
        } catch (Exception e) {
            log.error("Error de variables de encriptacion: {}", e.getMessage(), e);
            throw new RuntimeException("Error al inicializar EncryptionService: " + e.getMessage(), e);
        }

    }

    public String encrypt(String data) throws Exception {
        if (secretKeySpec == null) {
            throw new IllegalStateException("EncryptionService no está inicializado correctamente. Verifique la configuración de 'encryption.key'");
        }
        if (data == null) {
            throw new IllegalArgumentException("Los datos a encriptar no pueden ser null");
        }
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData);
    }
}
