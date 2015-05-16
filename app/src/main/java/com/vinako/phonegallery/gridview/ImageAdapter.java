package com.vinako.phonegallery.gridview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.vinako.phonegallery.checkbox.Model;
import com.vinako.phonegallery.phone.ImageStorage;
import com.vinako.phonegallery.phone.Phone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khue on 15/5/2015.
 */
public class ImageAdapter extends BaseAdapter {

    public static final String TAG = "ImageAdapter";

    private Context mContext;
    private List<Phone> allPhones;
    private List<Model> selectedModel;
    private List <Bitmap> bitmapList ;
   // private Bitmap[] bitmapArray;
    private String [] imageName;

    public ImageAdapter(Context c, List<Phone> phones, List<Model> model) {
        mContext = c;
        this.allPhones = phones;
        this.selectedModel = model;
        this.imageName = new String[allPhones.size()];
        this.bitmapList  = new ArrayList<Bitmap>();
        //set Image
        int i = 0;
        for( Model m : selectedModel){
            for( Phone p : allPhones){
                if( p.getCategory().equals(m.getCategory())){
                    if(ImageStorage.checkIfImageExist(p.getName()) == false) {
                        Log.d(TAG, "bitmap is null");
                    } else {
                        Bitmap bitmap = ImageStorage.getImageBitmap(p.getName());
                        Log.d(TAG, "bitmap is not null" + "Model selected is" + m.getCategory() + " phone selected is" + p.getName() );
                        imageName[i] = p.getName();
                        i++;
                        bitmapList.add(bitmap);
                    }

                }
            }
        }
        Log.d(TAG, "bitmap size is " + bitmapList.size());

//        bitmapArray = new Bitmap[bitmapList.size() + 1];
//        i = 0;
//        for (Bitmap m : bitmapList){
//            bitmapArray[i] = m;
//            i++;
//        }

    }

    public String[] getImageName(){
        return  this.imageName;
    }

    public int getCount() {
        return bitmapList.size();
//        return bitmapList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(120, 120));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }



        //imageView.setImageResource(mThumbIds[position]);

            imageView.setImageBitmap(bitmapList.get(position));
          //  imageView.setImageBitmap(bitmapList.get(position)[position]);

        return imageView;
    }


}