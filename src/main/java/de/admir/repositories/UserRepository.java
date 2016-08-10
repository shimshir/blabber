package de.admir.repositories;

import de.admir.models.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

/**
 * Author: Admir Memic
 * E-mail: me.admir@gmail.com
 * Date: 08.08.2016
 */
@Repository
@RestResource(exported = false)
public interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String username);
}
