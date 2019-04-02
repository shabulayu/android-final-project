package com.example.finalproject.NewsFeed;

public class Article {
    String text;
    String author;
    String title;
    String url;

    Article(){
    }

    Article(String title,String text,String aurhor,String url){
        this.title = title;
        this.text = text;
        this.author = aurhor;
        this.url = url;
    }

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
}
