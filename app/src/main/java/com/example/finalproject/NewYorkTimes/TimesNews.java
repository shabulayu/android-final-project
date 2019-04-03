package com.example.finalproject.NewYorkTimes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalproject.R;

/**
 * This is a article class, to define the attributes of an article
 */
public class TimesNews {

    //article attributes
    private long id;
    private String title;
    private String author;
    private String link;
    private String description;

    /**
     * default constructor
     */
    public TimesNews() {

    }

    /**constructor
     * @param title
     * @param author
     * @param link
     * @param description
     */
    public TimesNews(String title, String author, String link, String description) {
        this.title = title;
        this.author = author;
        this.link = link;
        this.description = description;
    }
    public TimesNews(long id, String title, String author, String link, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.link = link;
        this.description = description;
    }
    /**
     * getter
     * @return
     */
    public long getId() {
        return id;
    }
    /**
     * setter
     * @return
     */
    public void setId(long id) {
        this.id = id;
    }
    /**
     * getter
     * @return
     */
    public String getTitle() {
        return title;
    }
    /**
     * setter
     * @return
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * getter
     * @return
     */
    public String getAuthor() {
        return author;
    }
    /**
     * setter
     * @return
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    /**
     * getter
     * @return
     */
    public String getLink() {
        return link;
    }
    /**
     * setter
     * @return
     */
    public void setLink(String link) {
        this.link = link;
    }
    /**
     * getter
     * @return
     */
    public String getDescription() {
        return description;
    }
    /**
     * setter
     * @return
     */
    public void setDescription(String description) {
        this.description = description;
    }

}