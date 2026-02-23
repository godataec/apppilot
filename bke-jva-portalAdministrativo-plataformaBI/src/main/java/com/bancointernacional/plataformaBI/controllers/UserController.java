package com.bancointernacional.plataformaBI.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancointernacional.plataformaBI.models.DTO.UserDTO;
import com.bancointernacional.plataformaBI.models.entities.User;
import com.bancointernacional.plataformaBI.services.UserService;

import javax.validation.Valid;


@RestController
@RequestMapping("/users")
@CrossOrigin(originPatterns = "*")
public class UserController {
    private static final Logger userServImp = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @GetMapping
    public List<UserDTO> list(){
        return userService.findAll();
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> show(@PathVariable (name ="email") String email){
        userServImp.info("endpoint de busqueda de usuarios");
        Optional<UserDTO> userOptional = userService.findByEmail(email);
        
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.orElseThrow(null));
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/adding")
    public ResponseEntity<?> verifyandAdd (@Valid @RequestBody User user, BindingResult result)
    {
        userServImp.info("endpoint de verificacion y creacion de  usuarios");
        if(result.hasErrors()){
            return validation(result);
        }
        Optional<UserDTO> userVerifield =    userService.verifyAndAddUser(user);

        if(userVerifield.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(userVerifield.orElseThrow(null));
        }
        return  ResponseEntity.notFound().build();
    }

    @PostMapping 
    public ResponseEntity<?> create (@Valid @RequestBody User user, BindingResult result){
        userServImp.info("endpoint de creacion de usuarios");
        if(result.hasErrors()){
            return validation(result);
        }
        UserDTO userDB = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDB);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result){
        userServImp.info("endpoint de actualizacion de usuarios");
        if(result.hasErrors()){
            return validation(result);
        }
        Optional<UserDTO> o = userService.update(user, user.getEmail());
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow(null));
        }
        return  ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{email}")
    public ResponseEntity<?> remove(@PathVariable String email){
        userServImp.info("endpoint de borrado de usuarios");
       Optional<UserDTO> o = userService.findByEmail(email);
       if(o.isPresent()){
        userService.remove(email);
        return ResponseEntity.noContent().build();//204
       }
        return ResponseEntity.notFound().build();
    }



    private ResponseEntity<?> validation(BindingResult result) {
        userServImp.info("endpoint de validacion de usuarios");
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo "+err.getField()+" "+err.getDefaultMessage());
        });        
        return ResponseEntity.badRequest().body(errors);
    }

}
