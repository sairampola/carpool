package com.androidbelieve.drawerwithswipetabs;

/**
 * Created by pain on 17/2/17.
 */

import java.util.ArrayList;
public class LiftResult {

    private String name, thumbnailUrl;
    //private int year;
    private String saddr,daddr;

   // private ArrayList<String> genre;

    public LiftResult() {
    }

    public LiftResult(String name, String thumbnailUrl, String rating,
                 String genre) {
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        //this.year = year;
        this.saddr = rating;
        this.daddr = genre;
    }

    public String getTitle() {
        return name;
    }

    public void setTitle(String name) {
        this.name = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }



    public String getRating() {
        return saddr;
    }

    public void setRating(String rating) {
        this.saddr = rating;
    }

    public String getGenre() {
        return daddr;
    }

    public void setGenre(String genre) {
        this.daddr = genre;
    }

}




