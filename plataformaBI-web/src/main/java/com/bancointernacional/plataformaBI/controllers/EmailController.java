package com.bancointernacional.plataformaBI.controllers;

import com.bancointernacional.plataformaBI.models.DTO.EmailDTO;
import com.bancointernacional.plataformaBI.models.DTO.UserDTO;
import com.bancointernacional.plataformaBI.services.serviceImpl.EmailService;
import com.bancointernacional.plataformaBI.services.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/send-email")
    public String sendEmail() {
        emailService.sendSimpleEmail("CORREO DEL RECEIPT", "ASUNTO", "PRUEBA PARA BANCO INTERNACIONAL");
        return "Correo enviado";
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendEmailToUser(@RequestBody EmailDTO request) {
        String email = "";
        Optional<UserDTO> opcionalUser = userService.findByOpenId(request.getUserId().toString());
        if(opcionalUser.isPresent()) {
            email = opcionalUser.get().getEmail();
            emailService.sendSimpleEmail(email, "Message from System", request.getMessage());
        }
        return new ResponseEntity<>("Email sent to " + email, HttpStatus.OK);
    }
}