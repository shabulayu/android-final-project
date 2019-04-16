package com.example.finalproject.Dictionary;

import java.util.ArrayList;

public class DictionaryList {
    private String message;
    private long msg_id;
//    private ArrayList arr=new ArrayList();
    private String gson;


    public DictionaryList() {
        this(null, -1,null);
    }

    public DictionaryList(String message, long id,String a) {
        this.setContent(message);
        this.setMsg_id(id);
        this.setString(a);
    }

    public String getContent() {
        return message;
    }

    private void setContent(String content) {
        this.message = content;
    }

    private void setMsg_id(long id){this.msg_id=id;}
    private void setString(String a){
        this.gson=a;
//        a.forEach(b->arr.add(b));
//        for(Object b:a){
//           arr.add(b);
//        }
        }
    public long getMsg_id(){return msg_id;}

    public String getStr() {
        return gson;
    }

    @Override
    public String toString() {
        return this.getContent();
    }
}
