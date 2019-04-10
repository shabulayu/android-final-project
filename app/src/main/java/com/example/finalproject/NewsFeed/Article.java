package com.example.finalproject.NewsFeed;

/**
 * This class is an entity class
 */
public class Article {
    String text;
    String author;
    String title;
    String url;
    long id;

    Article(){
    }

    Article(String title,String text,String author,String url, long id){
        this.title = title;
        this.text = text;
        this.author = author;
        this.url = url;
        this.id = id;
    }

    /**
     * setter and getter methods
     * @param title
     */
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return title;
    }
    public void setText(String text){
        this.text = text;
    }
    public String getText(){
        return text;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public String getAuthor(){
        return author;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return url;
    }
    public void setId(long id){
        this.id = id;
    }
    public long getId(){
        return id;
    }
}
