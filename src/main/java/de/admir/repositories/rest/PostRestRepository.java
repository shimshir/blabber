package de.admir.repositories.rest;

import de.admir.models.Post;

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
public interface PostRestRepository extends JpaRepository<Post, String> {
    @Query(value = "select * from post join category_posts on post.id = posts_id join category on category.id = category_id where category_id = ?1", nativeQuery = true)
    List<Post> findByCategoryId(String categoryId);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    <P extends Post> List<P> save(Iterable<P> posts);

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'RIGHT_MODIFY_POST_' + #post.id)")
    <P extends Post> P saveAndFlush(@Param("post") P post);

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'RIGHT_MODIFY_POST_' + #post.id)")
    <P extends Post> P save(@Param("post") P post);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void deleteInBatch(Iterable<Post> posts);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void deleteAllInBatch();

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'RIGHT_MODIFY_POST_' + #postId)")
    void delete(@Param("postId") String postId);

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'RIGHT_MODIFY_POST_' + #post.id)")
    void delete(@Param("post") Post post);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void delete(Iterable<? extends Post> entities);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void deleteAll();
}
