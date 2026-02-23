package com.bancointernacional.plataformaBI.controller.utility;

import com.bancointernacional.plataformaBI.domain.dto.settings.UserDTO;
import com.bancointernacional.plataformaBI.domain.dto.utility.EmailDTO;
import com.bancointernacional.plataformaBI.service.EmailService;
import com.bancointernacional.plataformaBI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Value("${email.sending.enabled:true}")
    private boolean emailSendingEnabled;

    @GetMapping("/send-email")
    public String sendEmail() {
        if (!emailSendingEnabled) {
            return "Email sending is disabled - email not sent";
        }
        emailService.sendSimpleEmail("CORREO DEL RECEIPT", "ASUNTO", "PRUEBA PARA BANCO INTERNACIONAL");
        return "Correo enviado";
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendEmailToUser(@RequestBody EmailDTO request) {
        if (!emailSendingEnabled) {
            return new ResponseEntity<>("Email sending is disabled - email not sent", HttpStatus.OK);
        }

        String email = "";
        Optional<UserDTO> opcionalUser = userService.findUserByOpenId(request.getUserId().toString());
        if(opcionalUser.isPresent()) {
            email = opcionalUser.get().getEmail();
            emailService.sendSimpleEmail(email, "Message from System", request.getMessage());
        }
        return new ResponseEntity<>("Email sent to " + email, HttpStatus.OK);
    }
}