package com.bancointernacional.plataformaBI.service;

import com.bancointernacional.plataformaBI.filters.EncryptionService;
import com.bancointernacional.plataformaBI.domain.dto.settings.UserDTO;
import com.bancointernacional.plataformaBI.domain.model.settings.SubsidiaryUser;
import com.bancointernacional.plataformaBI.domain.model.settings.Subsidiary;
import com.bancointernacional.plataformaBI.mapper.UserMapper;
import com.bancointernacional.plataformaBI.repository.settings.SubsidiaryRepository;
import com.bancointernacional.plataformaBI.repository.settings.SubsidiaryUserRepository;
import com.bancointernacional.plataformaBI.service.usecase.UserUseCase;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService implements UserUseCase {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    SubsidiaryUserRepository subsidiaryUserRepository;

    @Autowired
    SubsidiaryRepository subsidiaryRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    private EncryptionService encryptServ;

    @Override
    public List<UserDTO> findAllUsers() {
        logger.info("Comienza proceso de recuperacion de usuarios!");
        List<SubsidiaryUser> subsidiaryUsers = subsidiaryUserRepository.findAllActive();
        return subsidiaryUsers
                .stream()
                .map(this::decryptUser)
                .filter(u -> "true".equals(u.getUserStatus()))
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> findUserByOpenId(String openIdUser) {
        logger.info("Comienza proceso de busqueda de usuarios por correo");
        return subsidiaryUserRepository
                .findByOpenIdUser(openIdUser)
                .map(u -> {
                    SubsidiaryUser us = decryptUser(u);
                    return userMapper.toDTO(us);
                });
    }

    @Override
    public UserDTO saveUser(UserDTO user) {
        logger.info("Guardando informacion de usuario :{}", user.getUserId());
        SubsidiaryUser us = new SubsidiaryUser();
        SubsidiaryUser decryptSubsidiaryUser = null;

        try {
            us.setUserId(user.getUserId());

            us = encryptUser(userMapper.toEntity(user));
            // Set subsidiary relationship properly
            Optional<Subsidiary> subsidiaryOpt = subsidiaryRepository.findById(1L); // Default subsidiary
            if (subsidiaryOpt.isPresent()) {
                us.setSubsidiary(subsidiaryOpt.get());
            } else {
                logger.warn("Default subsidiary not found, creating minimal subsidiary reference"); // Use helper method for backward compatibility
            }

            decryptSubsidiaryUser = decryptUser(subsidiaryUserRepository.save(us));
        } catch (Exception e) {
            logger.error("Error al desencriptar usuario: ", e);
        }
        return userMapper.toDTO(decryptSubsidiaryUser);
    }

    @Override
    public Optional<UserDTO> updateUser(UserDTO user, String openIdUser) {
        logger.info("Comienza proceso de actualizacion de usuarios");
        Optional<SubsidiaryUser> o = subsidiaryUserRepository.findByOpenIdUser(openIdUser);

        if (o.isPresent()) {
            SubsidiaryUser subsidiaryUserDB = decryptUser(o.get());
            subsidiaryUserDB.setOpenIdUser(user.getOpenIdUser());
            subsidiaryUserDB.setUserName(user.getName());
            subsidiaryUserDB.setUserLastName(user.getLastName());

            SubsidiaryUser subsidiaryUserCrypt = encryptUser(subsidiaryUserDB);
            SubsidiaryUser userforDescrypt = subsidiaryUserRepository.save(subsidiaryUserCrypt);
            SubsidiaryUser subsidiaryUserOptional = decryptUser(userforDescrypt);

            return Optional.of(userMapper.toDTO(subsidiaryUserOptional));
        }

        return Optional.empty();
    }

    @Override
    public void removeUserByEmail(String openIdUser) {
        logger.info("Comienza proceso de borrado de usuarios");
        Optional<SubsidiaryUser> o = subsidiaryUserRepository.findByOpenIdUser(openIdUser);

        if (o.isPresent()) {
            SubsidiaryUser subsidiaryUserDB = o.get();
            subsidiaryUserDB.setUserStatus("false");
            subsidiaryUserDB.setDeleted(true); // Also set soft delete flag
            subsidiaryUserRepository.save(subsidiaryUserDB);
        }
    }

    @Override
    public Optional<UserDTO> verifyAndSaveUser(UserDTO user) {
        Optional<SubsidiaryUser> o = subsidiaryUserRepository.findByOpenIdUser(user.getOpenIdUser());
        logger.info("Comienza proceso de verficacion de usuarios");

        if (o.isPresent()) {
            SubsidiaryUser subsidiaryUserDB = decryptUser(o.get());
            if (!subsidiaryUserDB.getUserName().equals(user.getName()) ||
                !subsidiaryUserDB.getUserLastName().equals(user.getLastName())) {
                return updateUser(user, user.getOpenIdUser());
            } else {
                return Optional.of(user);
            }
        } else {
            return Optional.of(saveUser(user));
        }
    }

    public SubsidiaryUser encryptUser(SubsidiaryUser subsidiaryUser) {
        SubsidiaryUser us = new SubsidiaryUser();
        try {
            us.setUserId(subsidiaryUser.getUserId());
            us.setOpenIdUser(subsidiaryUser.getOpenIdUser());
            us.setUserName(encryptServ.encrypt(subsidiaryUser.getUserName()));
            us.setUserLastName(encryptServ.encrypt(subsidiaryUser.getUserLastName()));
            us.setUserStatus(subsidiaryUser.getUserStatus());
            us.setRole(subsidiaryUser.getRole());
            us.setEmail(subsidiaryUser.getEmail());
            us.setSubsidiary(subsidiaryUser.getSubsidiary()); // Preserve subsidiary relationship
            us.setDeleted(subsidiaryUser.isDeleted());
            us.setCreatedAt(subsidiaryUser.getCreatedAt());
            us.setUpdatedAt(subsidiaryUser.getUpdatedAt());
        } catch (Exception e) {
            logger.error("Error al encriptar usuario: ", e);
        }
        return us;
    }

    public SubsidiaryUser decryptUser(SubsidiaryUser subsidiaryUser) {
        SubsidiaryUser us = new SubsidiaryUser();
        try {
            us.setUserId(subsidiaryUser.getUserId());
            us.setOpenIdUser(subsidiaryUser.getOpenIdUser());
            us.setUserName(encryptServ.decrypt(subsidiaryUser.getUserName()));
            us.setUserLastName(encryptServ.decrypt(subsidiaryUser.getUserLastName()));
            us.setUserStatus(subsidiaryUser.getUserStatus());
            us.setRole(subsidiaryUser.getRole());
            us.setEmail(subsidiaryUser.getEmail());
            us.setSubsidiary(subsidiaryUser.getSubsidiary()); // Preserve subsidiary relationship
            us.setDeleted(subsidiaryUser.isDeleted());
            us.setCreatedAt(subsidiaryUser.getCreatedAt());
            us.setUpdatedAt(subsidiaryUser.getUpdatedAt());
        } catch (Exception e) {
            logger.error("Error al desencriptar usuario: ", e);
        }
        return us;
    }
}
