package de.admir.services;

import de.admir.models.User;
import de.admir.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Author: Admir Memic
 * E-mail: me.admir@gmail.com
 * Date: 09.08.2016
 */
@Service
public class BlabberUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public BlabberUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User blabberUser = userRepository.findByUsername(username);
        if (blabberUser != null) {
            return new org.springframework.security.core.userdetails.User(blabberUser.getUsername(), blabberUser.getPasswordHash(), true, true, true, true,
                    blabberUser.getUsername().equals("admin") ? AuthorityUtils.createAuthorityList("ROLE_ADMIN") : AuthorityUtils.createAuthorityList("ROLE_USER"));
        } else {
            throw new UsernameNotFoundException("could not find the user '"
                    + username + "'");
        }
    }
}
