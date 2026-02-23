package com.bancointernacional.plataformaBI.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bancointernacional.plataformaBI.models.DTO.UserDTO;
import com.bancointernacional.plataformaBI.models.entities.User;
import com.bancointernacional.plataformaBI.repositories.UserRepository;



@Service
public class UserServiceImpl implements UserService {

    private static final Logger userServImp = LogManager.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        userServImp.info("Comienza proceso de recuperacion de usuarios!");
        List<User> users = userRepository.findByActiveTrue();
        // List<User> users =(List<User>) userRepository.findAll();
        return users
                 .stream()
                 .map(u -> UserDTO.build(u))
                 .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findByEmail(String email) {
        userServImp.info("Comienza proceso de busqueda de usuarios por correo");
        return userRepository
                .findByEmail(email)
                .map(u -> UserDTO.build(u));
    }

    @Override
    @Transactional
    public UserDTO save(User user) {
        userServImp.info("Comienza proceso de guardado de usuarios");
        return UserDTO.build(userRepository.save(user));
    }

    @Override
    @Transactional
    public Optional<UserDTO> update(User user, String email) {
        userServImp.info("Comienza proceso de actualizacion de usuarios");
        Optional<User> o = userRepository.findByEmail(email);
        User userOptional = null;

        if(o.isPresent())
        {
            User userDB = o.orElseThrow(null);
            userDB.setName(user.getName());
            userDB.setLastName(user.getLastName());
            userDB.setWorkPosition(user.getWorkPosition());
            userDB.setEmail(user.getEmail());

            userOptional = userRepository.save(userDB);
        }

            return Optional.ofNullable(UserDTO.build(userOptional));

    }

    @Override
    @Transactional
    public void remove(String email) {
        userServImp.info("Comienza proceso de borrado de usuarios");
        Optional<User> o = userRepository.findByEmail(email);


        if(o.isPresent())
        {
            User userDB = o.orElseThrow(null);
            userDB.setActive(false);

            userRepository.save(userDB);
        }

    }

    @Override
    @Transactional
    public Optional<UserDTO> verifyAndAddUser(User user){
        Optional<User> o = userRepository.findByEmail(user.getEmail());
        Optional<UserDTO> us = null;
        userServImp.info("Comienza proceso de verficacion de usuarios");
        if(o.isPresent())
        {
            us = update(user,user.getEmail());
        }else
        {
            us = Optional.of(save(user));
        }

        return us;
    }



   
}
