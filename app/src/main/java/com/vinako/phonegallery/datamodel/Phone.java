package com.vinako.phonegallery.datamodel;

import java.util.Date;
import java.util.Random;

/**
 * Created by Khue on 15/5/2015.
 */
public class Phone {
    private  String name;
    private String category;
    private String link;
    private long createdAt;
    private boolean downloaded;

    public Phone( String name, String category,String link){
        this.category = category;
        this.name = name;
        this.link = link;
        this.downloaded = false;

        Date date = new Date();
        Random rand = new Random();
        int  n = rand.nextInt(150) + 1;
        long unixTime = date.getTime()/1000 - n;
        this.createdAt = unixTime;
    }

    public String getCategory(){
        return this.category;
    }
    public String getLink(){
        return this.link;
    }
    public String getName() {return  this.name; }
    public long getCreatedAt() {return  this.createdAt; }


   public void setCreatedAt(long i){
       this.createdAt = i ;
   }

    public void setDownloaded(boolean val){
        this.downloaded = val;
    }

    public boolean getDownloaded(){
        return  this.downloaded;
    }


}
