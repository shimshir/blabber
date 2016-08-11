package de.admir.services;

import de.admir.models.User;
import de.admir.repositories.rest.CategoryRestRepository;
import de.admir.repositories.rest.CommentRestRepository;
import de.admir.repositories.rest.PostRestRepository;
import de.admir.repositories.rest.UserRestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Admir Memic
 * E-mail: me.admir@gmail.com
 * Date: 09.08.2016
 */
@Service
public class BlabberUserDetailsService implements UserDetailsService {
    private final PostRestRepository postRestRepository;
    private final UserRestRepository userRestRepository;
    private final CategoryRestRepository categoryRestRepository;
    private final CommentRestRepository commentRestRepository;

    @Autowired
    public BlabberUserDetailsService(PostRestRepository postRestRepository, UserRestRepository userRestRepository, CategoryRestRepository categoryRestRepository, CommentRestRepository commentRestRepository) {
        this.postRestRepository = postRestRepository;
        this.userRestRepository = userRestRepository;
        this.categoryRestRepository = categoryRestRepository;
        this.commentRestRepository = commentRestRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User blabberUser = userRestRepository.findByUsername(username);
        if (blabberUser != null) {
            return new org.springframework.security.core.userdetails.User(blabberUser.getUsername(), blabberUser.getPasswordHash(), true, true, true, true,
                    createAuthorityList(blabberUser));
        } else {
            throw new UsernameNotFoundException("could not find the user '"
                    + username + "'");
        }
    }

    private List<GrantedAuthority> createAuthorityList(User blabberUser) {
        if (blabberUser.getUsername().equals("admin"))
            return AuthorityUtils.createAuthorityList("ROLE_ADMIN");
        else {
            final List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            authorities.add(new SimpleGrantedAuthority("RIGHT_MODIFY_USER_" + blabberUser.getId()));
            if (blabberUser.getBlog() != null) {
                authorities.add(new SimpleGrantedAuthority("RIGHT_MODIFY_BLOG_" + blabberUser.getBlog().getId()));
                categoryRestRepository.findByBlogId(blabberUser.getBlog().getId()).stream()
                        .flatMap(cat -> {
                            authorities.add(new SimpleGrantedAuthority("RIGHT_MODIFY_CATEGORY_" + cat.getId()));
                            return postRestRepository.findByCategoryId(cat.getId()).stream();
                        })
                        .flatMap(post -> {
                            authorities.add(new SimpleGrantedAuthority("RIGHT_MODIFY_POST_" + post.getId()));
                            return commentRestRepository.findByPostId(post.getId()).stream();
                        }).forEach(comment -> authorities.add(new SimpleGrantedAuthority("RIGHT_MODIFY_COMMENT_" + comment.getId())));
            }
            return authorities;
        }
    }
}
