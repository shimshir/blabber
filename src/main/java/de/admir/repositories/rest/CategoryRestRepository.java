package de.admir.repositories.rest;

import de.admir.models.Category;

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
public interface CategoryRestRepository extends JpaRepository<Category, String> {
    @Query(value = "select * from category join blog_categories on category.id = categories_id join blog on blog.id = blog_id where blog_id = ?1", nativeQuery = true)
    List<Category> findByBlogId(String blogId);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    <C extends Category> List<C> save(Iterable<C> categories);

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'RIGHT_MODIFY_CATEGORY_' + #category.id)")
    <C extends Category> C saveAndFlush(@Param("category") C category);

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'RIGHT_MODIFY_CATEGORY_' + #category.id)")
    <C extends Category> C save(@Param("category") C category);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void deleteInBatch(Iterable<Category> categories);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void deleteAllInBatch();

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'RIGHT_MODIFY_CATEGORY_' + #categoryId)")
    void delete(@Param("categoryId") String categoryId);

    @Override
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'RIGHT_MODIFY_CATEGORY_' + #category.id)")
    void delete(@Param("category") Category category);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void delete(Iterable<? extends Category> entities);

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    void deleteAll();
}
