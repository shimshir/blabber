package de.admir.config;

import de.admir.models.User;
import de.admir.models.Blog;
import de.admir.models.Category;
import de.admir.models.Comment;
import de.admir.models.Post;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

/**
 * Author: Admir Memic
 * E-mail: me.admir@gmail.com
 * Date: 08.08.2016
 */
@Configuration
@EnableJpaRepositories(basePackages = "de.admir.repositories")
public class RestMvcConfiguration extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.setBasePath("/");
        config.exposeIdsFor(Blog.class, Category.class, Comment.class, Post.class, User.class);
    }
}
