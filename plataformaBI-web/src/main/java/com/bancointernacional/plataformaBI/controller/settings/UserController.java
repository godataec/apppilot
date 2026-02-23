package com.bancointernacional.plataformaBI.controller.settings;

import com.bancointernacional.plataformaBI.domain.dto.settings.UserDTO;
import com.bancointernacional.plataformaBI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Get all available users
     * @return List of all active users
     */
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserDTO> users = userService.findAllUsers();
            
            Map<String, Object> response = new HashMap<>();
            response.put("users", users);
            response.put("total", users.size());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error retrieving users: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Get user by OpenID
     * @param openUserId the OpenID of the user to retrieve
     * @return User details if found
     */
    @GetMapping("/{openUserId}")
    public ResponseEntity<?> getUserByOpenId(@PathVariable String openUserId) {
        try {
            Optional<UserDTO> userOptional = userService.findUserByOpenId(openUserId);
            
            if (userOptional.isPresent()) {
                return ResponseEntity.ok(userOptional.get());
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("error", "User not found with OpenID: " + openUserId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error retrieving user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
