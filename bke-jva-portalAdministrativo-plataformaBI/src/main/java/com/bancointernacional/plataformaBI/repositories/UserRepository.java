
package com.bancointernacional.plataformaBI.repositories;


import java.util.Optional;


import org.springframework.data.repository.CrudRepository;

import com.bancointernacional.plataformaBI.models.entities.User;

import java.util.List;




public interface UserRepository 
        extends CrudRepository<User, Long>{

            Optional<User> findByEmail(String email);

            List<User> findByActiveTrue();
}   
