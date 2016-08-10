package de.admir.repositories.rest;

import de.admir.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

/**
 * Author: Admir Memic
 * E-mail: me.admir@gmail.com
 * Date: 08.08.2016
 */
@Repository
@PreAuthorize("hasRole('ROLE_USER')")
public interface UserRestRepository extends JpaRepository<User, String> {
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or #user.username == authentication.name")
    <U extends User> U save(@Param("user") U user);

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or #user.username == authentication.name")
    void delete(@Param("user") User user);
}
