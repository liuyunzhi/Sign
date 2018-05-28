package com.cdut.sign.util;

public class Note {

    private String title;
    private String content;
    private String time;

    public Note(){

    }

    public Note(String title, String content, String time){
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }
}
