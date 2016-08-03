package de.admir.models;

/**
 * Author: Admir Memic
 * E-mail: me.admir@gmail.com
 * Date: 03.08.2016
 */
public class User extends IdentifiableModel {
    private String firstName;
    private String lastName;
    private String username;
    private String passwordHash;
    private Blog blog;
}
