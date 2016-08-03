package de.admir.models;

import java.util.List;

/**
 * Author: Admir Memic
 * E-mail: me.admir@gmail.com
 * Date: 03.08.2016
 */
public class Blog extends IdentifiableModel {
    private String code;
    private String name;
    private List<Category> categories;
}
