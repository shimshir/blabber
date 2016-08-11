package de.admir.repositories.rest;

import de.admir.models.Blog;

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
public interface BlogRestRepository extends JpaRepository<Blog, String> {
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    <B extends Blog> List<B> save(Iterable<B> blogs);

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'RIGHT_MODIFY_BLOG_' + #blog.id)")
    <B extends Blog> B saveAndFlush(@Param("blog") B blog);

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'RIGHT_MODIFY_BLOG_' + #blog.id)")
    <B extends Blog> B save(@Param("blog") B blog);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void deleteInBatch(Iterable<Blog> blogs);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void deleteAllInBatch();

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'RIGHT_MODIFY_BLOG_' + #blogId)")
    void delete(@Param("blogId") String blogId);

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'RIGHT_MODIFY_BLOG_' + #blog.id)")
    void delete(@Param("blog") Blog blog);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void delete(Iterable<? extends Blog> entities);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void deleteAll();
}
