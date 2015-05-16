package com.vinako.phonegallery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vinako.phonegallery.gridview.GridViewActivity;
import com.vinako.phonegallery.phone.ImageStorage;
import com.vinako.phonegallery.phone.Phone;
import com.vinako.phonegallery.storage.Storage;

import java.util.Date;


public class DetailActivity extends Activity {
    public static final String TAG = "DetailActivity";
    private String imageName;
    private TextView title;
    private TextView type;
    private TextView time;
    private Button btn;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            imageName = bundle.getString("imageName");
            Log.d(TAG,"Image name receive is" + imageName );
        }

        //get view
        title = (TextView) findViewById(R.id.detail_title);
        type = (TextView) findViewById(R.id.detail_type);
        time = (TextView) findViewById(R.id.detail_time);
        btn = (Button) findViewById(R.id.detail_btn);
        imageView = (ImageView) findViewById(R.id.detail_image);

        //getPhone
        Phone p = getPhone(imageName);
        title.setText("Image Title: " + p.getName());
        type.setText("Image Type: .jpg");


        //java.util.Date time=new java.util.Date((long)timeStamp*1000);
        Log.d(TAG, "Unix Time " + p.getCreatedAt());
        Date date = new Date((long) p.getCreatedAt()*1000);
        Log.d(TAG, "toString " + date.toString());
        Log.d(TAG, "toLocaleString " + date.toLocaleString());
        Log.d(TAG, "toGMTString " + date.toGMTString());
        Date currentDate = new Date();
        Log.d(TAG, "current Unix timme " + currentDate.getTime() / 1000 + " current time " + currentDate.toString());

        String text = "Created Date: " + date.toLocaleString();
        time.setText(text);

        //Image view
        Bitmap bitmap = ImageStorage.getImageBitmap(p.getName());
        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
        }

        //Button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), GridViewActivity.class));
            }
        });
    }

    public Phone getPhone(String name){

        for(Phone p: Storage.phones){
            if(p.getName().equals(name))
                return p;
        }
        return null;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      switch (item.getItemId()){
          case R.id.action_home:
              startActivity(new Intent(this, MainActivity.class));
              return true;
          default:
              return false;
      }


    }
}
