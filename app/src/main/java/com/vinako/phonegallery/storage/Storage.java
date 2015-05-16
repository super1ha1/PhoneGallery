package com.vinako.phonegallery.storage;

import com.vinako.phonegallery.checkbox.Model;
import com.vinako.phonegallery.phone.Phone;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Khue on 15/5/2015.
 */
public class Storage {
    public static List<Phone> phones =populateData() ;
    public static List<Model> list = getList();
    public static List<Model> selectedList;
    public static Hashtable<String, Integer> notificationID = getNotificationID();
    public static int defaultBackgroudColor;
//    public static String


    public static Hashtable<String, Integer> getNotificationID(){
        Hashtable<String, Integer> map = new Hashtable<>();
        map.put("android",1);
        map.put("windowphone",2);
        map.put("ios",3);
        map.put("j2me",4);
        map.put("blackberry",5);
        return map;

    }

    public static List<Phone> populateData() {
        phones = new ArrayList<Phone>();
        phones.add(getPhone("android1", "android", "http://i.imgur.com/RHcfcJC.jpg"));
        phones.add(getPhone("android2", "android", "http://i.imgur.com/qhGQWxl.jpg"));
        phones.add(getPhone("windowphone1", "windowphone", "http://i.imgur.com/PrjPgNb.jpg"));
        phones.add(getPhone("windowphone2", "windowphone", "http://i.imgur.com/DNCsrt0.jpg"));
        phones.add(getPhone("j2me1", "j2me", "http://i.imgur.com/2I2M7en.jpg"));
        phones.add(getPhone("j2me2", "j2me", "http://i.imgur.com/v5DqJLm.jpg"));
        phones.add(getPhone("ios1", "ios", "http://i.imgur.com/YkR4jZc.jpg"));
        phones.add(getPhone("ios2", "ios", "http://i.imgur.com/Yhf5ibV.jpg"));
        phones.add(getPhone("blackberry1", "blackberry", "http://i.imgur.com/ls6FGHG.jpg"));
        phones.add(getPhone("blackberry2", "blackberry", "http://i.imgur.com/SHVZDOx.jpg"));
        return phones;
    }

    public  static Phone getPhone(String name, String category, String link){
        return new Phone(name, category, link);
    }



    public static List<Model> getList(){
        List<Model> list = new ArrayList<Model>();
        list.add(getModel("android"));
        list.add(getModel("windowphone"));
        list.add(getModel("j2me"));
        list.add(getModel("ios"));
        list.add(getModel("blackberry"));

        return list;

    }

    public static Model getModel(String category){
        return new Model(category);
    }
    public static Phone getPhone(String name){
        for( Phone p : phones){
            if( p.getName().equals(name))
                return  p;
        }
        return null;
    }

}
