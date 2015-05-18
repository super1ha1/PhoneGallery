package com.vinako.phonegallery.storage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Khue on 15/5/2015.
 */
public class ImageStorage {
    public static  final String DIRECTORY = "/phoneGallery";
    public static String saveToSdCard(Bitmap bitmap, String fileName){
        String stored = null;
        String sdcard = Environment.getExternalStorageDirectory().toString();
//        File folder = new File(sdcard.getAbsoluteFile(),DIRECTORY);
        File folder = new File(sdcard + DIRECTORY);
        if(!folder.exists()){
            if(folder.mkdir())
               Log.d("Folder exist", "make dirs");
        }
        //folder.mkdir();

        File file = new File(folder.getAbsoluteFile(),fileName + ".jpg");
        if(file.exists()) {
            Log.d("File", "exist");
            return stored;
        }

        try{
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            stored ="success";
            Log.d("Stored", "success");
        }catch (Exception e){
            Log.d("ImageStorage", "Can write new File");
        }
        return stored;
    }
    public  static File getImage(String fileName){
        File image = null;
        try{
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root);
            if(!myDir.exists()) return null;
            image = new File(myDir.getPath()+ DIRECTORY + fileName);
            Log.d("GetImage", "success");
        }catch (Exception e){
            Log.d("ImageStorage","Can not load file");
        }
        return image;
    }
    public  static  boolean checkIfImageExist(String fileName){
        Bitmap b = null;
        File file = ImageStorage.getImage("/" + fileName + ".jpg");
        String path = file.getAbsolutePath();
        if(path != null)
            b = BitmapFactory.decodeFile(path);
        if( b == null || b.equals("")) return false;

        return true;
    }
    public static Bitmap getImageBitmap(String fileName){

        Bitmap b = null;
        File file = ImageStorage.getImage("/" + fileName + ".jpg");
        String path = file.getAbsolutePath();
        if(path != null)
            b = BitmapFactory.decodeFile(path);
        return b;
    }
}
