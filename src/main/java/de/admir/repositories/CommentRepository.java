package de.admir.repositories;

import de.admir.models.Comment;

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
public interface CommentRepository extends JpaRepository<Comment, String> {
}
