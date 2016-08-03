package de.admir.models;

import java.util.List;

/**
 * Author: Admir Memic
 * E-mail: me.admir@gmail.com
 * Date: 03.08.2016
 */
public class Post extends IdentifiableModel {
    private String title;
    private String content;
    private List<Comment> comments;
}
