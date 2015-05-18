package com.vinako.phonegallery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.vinako.phonegallery.R;
import com.vinako.phonegallery.datamodel.Model;
import com.vinako.phonegallery.storage.Storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khue on 15/5/2015.
 */
public class CheckBoxAdapter extends ArrayAdapter<Model> {
    private Context context;
    private List<Model> list;

    public CheckBoxAdapter(Context context, List<Model> list) {
        super(context, R.layout.fragment_phone , list);
        this.context= context;
        this.list = list;
    }

    static class ViewHolder{
        protected TextView category;
        protected TextView  status;
        protected ImageView icon;
        protected CheckBox checkBox;
    }

    public View getView( int position, View convertView, ViewGroup parent){
         View view = null;
        if( convertView == null){
            //create inflater from context
            LayoutInflater inflater = (LayoutInflater ) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //create new view from xml source
             view  =  inflater.inflate(R.layout.fragment_phone, null);
            final View currentView = view;
            //create a new ViewHolder
            final  ViewHolder viewHolder = new ViewHolder();
            viewHolder.category = (TextView)  view.findViewById(R.id.phone_category);
            viewHolder.status = (TextView) view.findViewById(R.id.phone_status);
            viewHolder.icon = (ImageView) view.findViewById(R.id.phone_icon) ;
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.phone_check_box);

            //Set Tag for Check box and setonChanged
            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Model element = (Model) viewHolder.checkBox.getTag();
                    element.setSelected(compoundButton.isChecked());
                    Storage.defaultBackgroudColor = currentView.getDrawingCacheBackgroundColor();

                    if(  element.getSelected())
                        currentView.setBackgroundColor(Color.CYAN);
                    else currentView.setBackgroundColor(Storage.defaultBackgroudColor);



                }
            });

            //set Tag for viewholder under view and check box under viewholder
            view.setTag(viewHolder);
            viewHolder.checkBox.setTag(list.get(position));
        }else{
            view = convertView;
            ((ViewHolder) view.getTag()).checkBox.setTag(list.get(position));
        }

        //now getTag viewholder of View and display the text from list
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        String text = "Show me " + list.get(position).getCategory() + " pictures";
        viewHolder.category.setText(text);
        viewHolder.checkBox.setChecked(list.get(position).getSelected());
        //viewHolder.icon.setImageDrawable(@Drawble/android1.jpg);

        //SEt Image
        Model m = getItem(position);
        int imageResource;
        switch (m.getCategory()){
            case "android":
                imageResource = R.drawable.android1;
                break;
            case "windowphone":
                imageResource =R.drawable.windowphone1;
                break;
            case "ios":
                imageResource =R.drawable.ios1;
                break;
            case "j2me":
                imageResource =R.drawable.j2me1;
                break;
            case "blackberry":
                imageResource =R.drawable.blackberry1;
                break;
            default:
                imageResource = R.drawable.android1;
                break;
        }
        //Get Image
        Drawable image = getContext().getApplicationContext().getResources().getDrawable(imageResource);
        viewHolder.icon.setImageDrawable(image);
        if( m.getUpdateText() == true)
             viewHolder.status.setText("Download Status: Done");
        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Model getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //return list of ArrayList Checked
    public  ArrayList<Model> getBox() {
        ArrayList<Model> box = new ArrayList<Model>();
        for (Model p : list) {
            if (p.getSelected())
                box.add(p);
        }
        return box;
    }
}
