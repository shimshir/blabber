package de.admir.repositories.rest;

import de.admir.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * Author: Admir Memic
 * E-mail: me.admir@gmail.com
 * Date: 08.08.2016
 */
@RepositoryRestResource
public interface UserRestRepository extends JpaRepository<User, String> {
    User findByUsername(String username);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    <U extends User> List<U> save(Iterable<U> iterableUsers);

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'RIGHT_MODIFY_USER_' + #user.id) or #user.id == null")
    <U extends User> U saveAndFlush(@Param("user") U user);

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'RIGHT_MODIFY_USER_' + #user.id) or #user.id == null")
    <U extends User> U save(@Param("user") U user);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void deleteInBatch(Iterable<User> users);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void deleteAllInBatch();

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'RIGHT_MODIFY_USER_' + #userId)")
    void delete(@Param("userId") String userId);

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'RIGHT_MODIFY_USER_' + #user.id)")
    void delete(@Param("user") User user);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void delete(Iterable<? extends User> users);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void deleteAll();
}
