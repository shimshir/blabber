package de.admir.repositories.rest;

import de.admir.models.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
public interface CommentRestRepository extends JpaRepository<Comment, String> {
    @Query(value = "select * from comment join post_comments on comment.id = comments_id join post on post.id = post_id where post_id = ?1", nativeQuery = true)
    List<Comment> findByPostId(String postId);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    <C extends Comment> List<C> save(Iterable<C> comments);

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'RIGHT_MODIFY_COMMENT_' + #comment.id)")
    <C extends Comment> C saveAndFlush(@Param("comment") C comment);

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'RIGHT_MODIFY_COMMENT_' + #comment.id)")
    <C extends Comment> C save(@Param("comment") C comment);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void deleteInBatch(Iterable<Comment> comments);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void deleteAllInBatch();

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'RIGHT_MODIFY_COMMENT_' + #commentId)")
    void delete(@Param("commentId") String commentId);

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'RIGHT_MODIFY_COMMENT_' + #comment.id)")
    void delete(@Param("comment") Comment comment);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void delete(Iterable<? extends Comment> entities);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void deleteAll();
}
