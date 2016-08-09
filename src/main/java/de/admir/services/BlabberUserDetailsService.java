package de.admir.services;

import de.admir.models.BlabberUser;
import de.admir.repositories.BlabberUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
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
    private final BlabberUserRepository blabberUserRepository;

    @Autowired
    public BlabberUserDetailsService(BlabberUserRepository blabberUserRepository) {
        this.blabberUserRepository = blabberUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BlabberUser blabberUser = blabberUserRepository.findByUsername(username);
        if (blabberUser != null) {
            return new User(blabberUser.getUsername(), blabberUser.getPasswordHash(), true, true, true, true,
                    blabberUser.getUsername().equals("admin") ? AuthorityUtils.createAuthorityList("ADMIN") : AuthorityUtils.createAuthorityList("USER"));
        } else {
            throw new UsernameNotFoundException("could not find the user '"
                    + username + "'");
        }
    }
}
