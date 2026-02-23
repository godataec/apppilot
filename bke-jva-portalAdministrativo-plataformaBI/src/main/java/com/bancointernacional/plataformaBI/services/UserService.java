package com.bancointernacional.plataformaBI.services;

import java.util.List;
import java.util.Optional;

import com.bancointernacional.plataformaBI.models.DTO.UserDTO;
import com.bancointernacional.plataformaBI.models.entities.User;



public interface UserService {

    List<UserDTO> findAll();

    Optional<UserDTO> findByEmail(String email);

    UserDTO save(User user);
    Optional<UserDTO> update(User user, String email);

    void remove(String email);

    Optional<UserDTO> verifyAndAddUser(User user);
}
