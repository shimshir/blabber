package de.admir.repositories.rest;

import de.admir.models.Blog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

/**
 * Author: Admir Memic
 * E-mail: me.admir@gmail.com
 * Date: 08.08.2016
 */
@Repository
@PreAuthorize("hasRole('ROLE_USER')")
public interface BlogRestRepository extends JpaRepository<Blog, String> {
}