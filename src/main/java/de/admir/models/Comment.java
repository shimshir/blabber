package de.admir.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Author: Admir Memic
 * E-mail: me.admir@gmail.com
 * Date: 03.08.2016
 */
@Entity
public class Comment extends IdentifiableModel {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "blabber_user_id")
    private BlabberUser blabberUser;
    private String content;

    public BlabberUser getBlabberUser() {
        return blabberUser;
    }

    public void setBlabberUser(BlabberUser blabberUser) {
        this.blabberUser = blabberUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
