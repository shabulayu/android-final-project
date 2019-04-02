package com.example.finalproject;

public class DictionaryList {
    private String message;
    private long msg_id;


    public DictionaryList() {
        this(null, -1);
    }

    public DictionaryList(String message, long id) {
        this.setContent(message);
        this.setMsg_id(id);

    }

    public String getContent() {
        return message;
    }

    private void setContent(String content) {
        this.message = content;
    }

    private void setMsg_id(long id){this.msg_id=id;}
    public long getMsg_id(){return msg_id;}


    @Override
    public String toString() {
        return this.getContent();
    }
}
