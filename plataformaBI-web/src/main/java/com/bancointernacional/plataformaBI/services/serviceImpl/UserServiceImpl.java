package com.bancointernacional.plataformaBI.services.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bancointernacional.plataformaBI.filters.EncryptionService;
import com.bancointernacional.plataformaBI.models.DTO.UserDTO;
import com.bancointernacional.plataformaBI.models.entities.User;
import com.bancointernacional.plataformaBI.repositories.UserRepository;
import com.bancointernacional.plataformaBI.services.serviceInterface.UserService;



@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EncryptionService encryptServ;

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        logger.info("Comienza proceso de recuperacion de usuarios!");
        List<User> users = (List<User>) userRepository.findAll();
        // List<User> users =(List<User>) userRepository.findAll();
        return users
                    .stream()
                    .map(this::decryptUser) // Mapea primero usando decryptUser
                    .filter(u -> u.getStatus().contains("true")) // Filtra los usuarios con status "true"
                    .map(UserDTO::build) // Luego convierte los usuarios filtrados en UserDTO
                    .collect(Collectors.toList());
    }

    //Toca Cambiar por la nueva funcion!
    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findByOpenId(String openIdUser) {
        logger.info("Comienza proceso de busqueda de usuarios por correo");
        return userRepository
                .findByOpenIdUser(openIdUser)
                .map(u -> {
                    User us = decryptUser(u);
                    return UserDTO.build(us);
                 });
    }

    @Override
    @Transactional
    public UserDTO save(User user) {
        logger.info("Guardando informacion de usuario :{}",user.getIdUser());
        User us = new User();
        User decryptUser = null;
        us.setId(user.getId());
        try {
            us = encryptUser(user);  
            decryptUser = decryptUser(userRepository.save(us));
        } catch (Exception e) {
            logger.error("Error al desencriptar usuario: ",e);
        }
        
        return UserDTO.build(decryptUser);
    }

    //Toca Cambiar por la nueva funcion!
    @Override
    @Transactional
    public Optional<UserDTO> update(User user, String openIdUser) {
        System.out.println("Openid:"+openIdUser);
        logger.info("Comienza proceso de actualizacion de usuarios");
        User userOptional = null;
        User userforDescrypt = null;
        Optional<User> o = userRepository.findByOpenIdUser(openIdUser);
        if(o.isPresent() )
        {
            User userDB  = decryptUser(o.orElseThrow(null));
            userDB.setOpenIdUser(user.getOpenIdUser());
            userDB.setName(user.getName());
            userDB.setLastName(user.getLastName());
            User userCrypt = encryptUser(userDB);
            userforDescrypt = userRepository.save(userCrypt);
            userOptional = decryptUser(userforDescrypt);
        }
        return Optional.ofNullable(UserDTO.build(userOptional));
    }

    //Toca Cambiar por la nueva funcion!
    @Override
    @Transactional
    public void remove(String openIdUser) {
        logger.info("Comienza proceso de borrado de usuarios");
        Optional<User> o = userRepository.findByOpenIdUser(openIdUser);
        if(o.isPresent())
        {
            User userDB = o.orElseThrow(null);
            userDB.setStatus("false");
            userRepository.save(userDB);
        }

    }

    @Override
    @Transactional
    public Optional<UserDTO> verifyAndAddUser(User user){
        Optional<User> o = userRepository.findByOpenIdUser(user.getOpenIdUser());
        System.out.println("Buscar por openid:"+user.getOpenIdUser());
        Optional<UserDTO> us = Optional.empty();
        logger.info("Comienza proceso de verficacion de usuarios");
        if(o.isPresent()) {
            User userDB  = decryptUser(o.get());
            if(!userDB.getName().equals(user.getName()) || !userDB.getLastName().equals(user.getLastName())){
                us = update(user,user.getOpenIdUser());
            }else {
                us = Optional.of( UserDTO.build(user));
            }
        }else {
            us = Optional.of(save(user));
        }
        return us;
    }

    public User  encryptUser (User user){
        User us = new User();
        try {
            us.setId(user.getId());
            us.setOpenIdUser(user.getOpenIdUser());
            us.setName(encryptServ.encrypt(user.getName()));
            us.setLastName(encryptServ.encrypt(user.getLastName()));
            us.setStatus(user.getStatus());
            us.setRole(user.getRole());
            us.setEmail(user.getEmail());
        } catch (Exception e) {
            logger.error("Error al encriptar usuario: ",e);
        }
        return us;
    } 

    public User decryptUser (User user) {
        User us = new User();
        try {
            us.setId(user.getId());
            us.setOpenIdUser(user.getOpenIdUser());
            us.setName(encryptServ.decrypt(user.getName()));
            us.setLastName(encryptServ.decrypt(user.getLastName()));
            us.setStatus(user.getStatus());
            us.setRole(user.getRole());
            us.setEmail(user.getEmail());
        } catch (Exception e) {
            logger.error("Error al desencriptar usuario: ",e);
        }
        return us;
    }


   
}
