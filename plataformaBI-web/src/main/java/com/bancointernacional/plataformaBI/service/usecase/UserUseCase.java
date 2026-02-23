package com.bancointernacional.plataformaBI.service.usecase;

import com.bancointernacional.plataformaBI.domain.dto.settings.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserUseCase {
    List<UserDTO> findAllUsers();
    Optional<UserDTO> findUserByOpenId(String OpenIdUser);
    UserDTO saveUser(UserDTO user);
    Optional<UserDTO> updateUser(UserDTO user, String OpenIdUser);
    void removeUserByEmail(String email);
    Optional<UserDTO> verifyAndSaveUser(UserDTO user);
}
