package de.admir.repositories;

import de.admir.models.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: Admir Memic
 * E-mail: me.admir@gmail.com
 * Date: 08.08.2016
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
}
