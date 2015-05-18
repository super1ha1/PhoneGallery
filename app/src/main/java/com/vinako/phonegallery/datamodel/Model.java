package com.vinako.phonegallery.datamodel;

/**
 * Created by Khue on 15/5/2015.
 */

public class Model {
    private  String category;
    private boolean selected;
    private boolean status;
    private boolean updateText;

    public Model(String category){
        this.category = category;
        this.selected = false;
        this.status = false;
        this.updateText = false;
    }

    public String getCategory(){
        return this.category;
    }
    public boolean getSelected(){
        return  this.selected;
    }
    public boolean getStatus(){return this.status; }
    public boolean getUpdateText(){return this.updateText; }

    public void setCategory(String name){
        this.category = name;
    }
    public  void setSelected(boolean selected){
        this.selected = selected;
    }
    public  void setStatus(boolean val){
        this.status = val;
    }

    public void setUpdateText(boolean val){
        this.updateText = val;
    }

}
