
package com.bancointernacional.plataformaBI.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import com.bancointernacional.plataformaBI.models.entities.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByOpenIdUser(String openIdUser);

    List<User> findByStatusTrue();
}   
