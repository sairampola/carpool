package com.androidbelieve.drawerwithswipetabs;

/**
 * Created by pain on 17/3/17.
 */

public class NotiPickupResults {
    private String name, thumbnailUrl;
    //private int year;
    private String Aemailid,Bemailid,status;


    // private ArrayList<String> genre;

    public NotiPickupResults() {
    }

    public NotiPickupResults(String name, String thumbnailUrl, String Aem,
                      String Bem,String sta) {
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
        //this.year = year;
        this.Aemailid = Aem;
        this.Bemailid = Bem;
        this.status=sta;
        //this.emailid=emid;
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



    public String getAemailid() {
        return Aemailid;
    }

    public void setAemailid(String Aem) {
        this.Aemailid = Aem;
    }

    public String getBemailid() {
        return Bemailid;
    }

    public void setBemailid(String Bem) {
        this.Bemailid = Bem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /*public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }*/


}
