package de.admir.config;

import de.admir.services.BlabberUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Author: Admir Memic
 * E-mail: me.admir@gmail.com
 * Date: 09.08.2016
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private BlabberUserDetailsService userDetailsService;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public WebSecurityConfiguration(BlabberUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/users", "/users/*").authenticated().and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/users", "/users/*").permitAll().and()
                .authorizeRequests().antMatchers(HttpMethod.PUT, "/users/*").authenticated().and()
                .authorizeRequests().antMatchers(HttpMethod.PATCH, "/users/*").authenticated().and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/comments/*/user", "/comments/*/user/*").authenticated().and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/users/*/blog").permitAll().and()
                .authorizeRequests().antMatchers(HttpMethod.PUT, "/blogs/**", "/posts/**", "/comments/**", "/categories/**").authenticated().and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/blogs/**", "/posts/**", "/comments/**", "/categories/**").authenticated().and()
                .authorizeRequests().antMatchers(HttpMethod.PATCH, "/blogs/**", "/posts/**", "/comments/**", "/categories/**").authenticated().and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/", "/blogs/**", "/posts/**", "/comments/**", "/categories/**").permitAll().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().disable();
    }
}
