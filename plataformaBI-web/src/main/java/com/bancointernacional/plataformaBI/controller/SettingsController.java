package com.bancointernacional.plataformaBI.controller;

import com.bancointernacional.plataformaBI.domain.dto.settings.ColorPalleteDTO;
import com.bancointernacional.plataformaBI.domain.dto.settings.UserDTO;
import com.bancointernacional.plataformaBI.domain.dto.azure.AzureSyncRequestDTO;
import com.bancointernacional.plataformaBI.domain.dto.azure.AzureSyncResponseDTO;
import com.bancointernacional.plataformaBI.service.ColorPaletteService;
import com.bancointernacional.plataformaBI.service.UserService;
import com.bancointernacional.plataformaBI.service.AzureADSyncService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RequestMapping("/settings")
@RestController
@Tag(name = "Settings", description = "Settings management including user synchronization and configuration")
public class SettingsController {

    private static final Logger settingsLogger = LogManager.getLogger(SettingsController.class);

    @Autowired
    UserService userService;

    @Autowired
    ColorPaletteService colorPaletteService;

    @Autowired
    AzureADSyncService azureADSyncService;

    @PostMapping("/synchronize-users")
    public ResponseEntity<?> synchronizeUsers(
            @RequestBody List<UserDTO> users,
            BindingResult result) {
        settingsLogger.info("Synchronizing users");
        if (result.hasErrors()) {
            return validation(result);
        }
        List<UserDTO> verifiedUsers = new ArrayList<>();
        for (UserDTO user : users) {
            Optional<UserDTO> userVerified = userService.verifyAndSaveUser(user);
            if (userVerified.isPresent()) {
                verifiedUsers.add(userVerified.get());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo verificar el usuario:" + user.getName() + " " + user.getLastName());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(verifiedUsers);
    }

    private ResponseEntity<?> validation(BindingResult result) {
        settingsLogger.info("endpoint de errores de usuarios");
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err ->
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Get all active color palettes from the database
     */
    @GetMapping("/color")
    public ResponseEntity<?> getAllColorPalettes(
            @RequestParam(name = "subsidiaryId", required = false) Long subsidiaryId) {
        try {
            settingsLogger.info("Retrieving color palettes for subsidiary: {}", subsidiaryId);

            List<ColorPalleteDTO> colorPalettes;

            if (subsidiaryId != null) {
                colorPalettes = colorPaletteService.getAllActiveColorPalettesBySubsidiary(subsidiaryId);
                settingsLogger.info("Retrieved {} color palettes for subsidiary {}", colorPalettes.size(), subsidiaryId);
            } else {
                colorPalettes = colorPaletteService.getAllActiveColorPalettes();
                settingsLogger.info("Retrieved {} total active color palettes", colorPalettes.size());
            }

            return ResponseEntity.ok(colorPalettes);

        } catch (Exception e) {
            settingsLogger.error("Error retrieving color palettes: {}", e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error retrieving color palettes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/sync-azure-ad-users")
    @Operation(
        summary = "Synchronize users from Azure AD",
        description = "Synchronizes users from Azure Active Directory using Microsoft Graph API. " +
                     "Automatically acquires Graph API token using configured Azure AD credentials.",
        security = @SecurityRequirement(name = "JWT")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Users synchronized successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AzureSyncResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error - Microsoft Graph API error or other system error"
        )
    })
    public ResponseEntity<?> syncAzureADUsers() {

        settingsLogger.info("Starting Azure AD user synchronization");


        try {
            // The service will acquire its own Graph token using client credentials
            azureADSyncService.synchronizeUsers(null);
            settingsLogger.info("Successfully synchronized users from Azure AD");

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(AzureSyncResponseDTO.builder()
                            .success(true)
                            .message("Users synchronized successfully")
                            .timestamp(System.currentTimeMillis())
                            .build());

        } catch (Exception e) {
            settingsLogger.error("Error during Azure AD synchronization: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(AzureSyncResponseDTO.builder()
                            .success(false)
                            .message("Error synchronizing users from Azure AD: " + e.getMessage())
                            .totalUsers(0)
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }

}
