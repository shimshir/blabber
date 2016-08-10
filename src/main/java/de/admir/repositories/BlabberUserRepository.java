package de.admir.repositories;

import de.admir.models.BlabberUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Repository;

/**
 * Author: Admir Memic
 * E-mail: me.admir@gmail.com
 * Date: 08.08.2016
 */
@Repository
public interface BlabberUserRepository extends JpaRepository<BlabberUser, String> {
    BlabberUser findByUsername(String username);

    @Override
    @PostAuthorize("hasRole('ROLE_ADMIN') or (#user.username == authentication.name and isFullyAuthenticated())")
    <S extends BlabberUser> S save(@Param("user") S blabberUser);

    @Override
    @PostAuthorize("hasRole('ROLE_ADMIN') or (#user.username == authentication.name and isFullyAuthenticated())")
    void delete(@Param("user") BlabberUser blabberUser);
}
