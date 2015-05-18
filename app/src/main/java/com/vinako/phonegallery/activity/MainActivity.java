package com.vinako.phonegallery.activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vinako.phonegallery.R;
import com.vinako.phonegallery.adapter.CheckBoxAdapter;
import com.vinako.phonegallery.datamodel.Model;
import com.vinako.phonegallery.storage.ImageStorage;
import com.vinako.phonegallery.datamodel.Phone;
import com.vinako.phonegallery.storage.Storage;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends Activity {

    public  static final String TAG  = "MainActivity";

    private Button btn_show;
    private Button btn_download;
    private ListView listView;
    private LinearLayout addFooterView;
    private List<String> categoryDownloading = new ArrayList<String>();
    private List<String> phoneDownloading = new ArrayList<String>();
    private CheckBoxAdapter adapter;

//    private List<Phone> phones;
//    private List<Model> list;
//    private List<Model> selectedList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }



    public void initView(){
        btn_show = (Button) findViewById(R.id.btn_show);
        btn_download = (Button) findViewById(R.id.btn_download);
        listView = (ListView) findViewById(R.id.list_view);
        addFooterView = (LinearLayout) findViewById(R.id.main_add);

        for( Model m: Storage.list){
            m.setSelected(false);
            m.setStatus(false);
            m.setUpdateText(false);
        }

        for( Phone p: Storage.phones){
            p.setDownloaded(false);
        }
        Storage.selectedList = null;
   //     Log.d(TAG, "after clear selectedList size is " + Storage.selectedList.size()  );


        adapter = new CheckBoxAdapter(this, Storage.list);

      //  listView.addFooterView(addFooterView, null, true);
        listView.setAdapter(adapter);


        //button show
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Storage.selectedList = adapter.getBox();
                startActivity(new Intent(getApplicationContext(), GridViewActivity.class));
            }
        });

       //Get list checked Item
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Storage.selectedList = adapter.getBox();
                for (Model m : Storage.selectedList) {
                    if (m.getSelected()) {
//                        result += "\n" + p.getCategory();
//                        totalAmount += 1;
                        for (Phone p : Storage.phones) {
                            if ( p.getCategory().equals(m.getCategory()) ) {
                                if(!categoryDownloading.contains(p.getCategory()))
                                     categoryDownloading.add(p.getCategory().toString());
                                if(!phoneDownloading.contains(p.getName()))
                                    phoneDownloading.add(p.getName().toString());

                                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                                NetworkInfo info = connMgr.getActiveNetworkInfo();
                                if(info != null && info.isConnected()){
                                    Log.d(TAG, "phone download " + phoneDownloading);
                                    Log.d(TAG, "category download " + categoryDownloading);
                                    new DownloadImage(p.getLink(), p.getName()).execute();
                                }else {
                                    Toast.makeText(getApplicationContext(), "No Network Available", Toast.LENGTH_LONG).show();
                                    Log.d(TAG, "No Network");
                                }


                            }
                        }

                    }
                }

            }
        });


    }


    private class DownloadImage extends AsyncTask<Object, Object, Object> {

        private String url = null, imageName = null;
        private OutputStream os;

        private Bitmap bitmap;

        public DownloadImage(String url, String imageName){
            this.url = url;

            this.imageName = imageName;
        }
        @Override
        protected Object doInBackground(Object... objects) {
            try {
                URL imageURL = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) imageURL.openConnection();
                //set detail
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            }catch (Exception e){
                Log.d("DownloadImage", "Can not download");
            }
            return bitmap;
        }
        protected void onPostExecute(Object o){
            if(!ImageStorage.checkIfImageExist(imageName)){
//                view.setImageBitmap(bitmap);
                Log.d(TAG, "Image have not exits, create a new file");
                ImageStorage.saveToSdCard(bitmap, imageName);
            }else{
                //Image already download
                Log.d(TAG, "Image already exits " + imageName);
            }
            //set for phone
            for (Phone p : Storage.phones) {
                if ( p.getName().equals(imageName))   {
                    p.setDownloaded(true);

                    Date currentDate = new Date();
                    p.setCreatedAt((long) (currentDate.getTime()/1000));
                    Log.d(TAG, "phone finished: " + p.getName() + " at " + p.getCreatedAt());
                }
            }

            //set for model
            for (Model m : Storage.selectedList) {
                if (categoryDownloading.contains(m.getCategory()) ) {
                    //initially set to true
                    m.setStatus(true);
                    for (Phone p : Storage.phones) {
                        if ( m.getCategory().equals(p.getCategory())  ) {
                            if (p.getDownloaded() == false) {
                                m.setStatus(false);
                            }
                        }
                    }
                }
            }
            for(Model m : Storage.selectedList){
//                if(m.getStatus()== true && m.getUpdateText()== false){ //Done Download
                if(m.getStatus()== true ){ //Done Download
                    Log.d(TAG, "Category done download: " + m.getCategory());


                    //Change status
                    int firstPosition = listView.getFirstVisiblePosition();
                    int currPosition = adapter.getPosition(m);
                    int target = currPosition > firstPosition ? currPosition - firstPosition : firstPosition;
                    if (currPosition == listView.getLastVisiblePosition())
                        target = listView.getLastVisiblePosition();
//                    if(adapter.getPosition(m) > firstPosition) {
//                        View currentView = listView.getChildAt(adapter.getPosition(m) - firstPosition); //current row
//                    }

                    Log.d(TAG,"first " + firstPosition  + " last " + listView.getLastVisiblePosition() + " current " + currPosition+ " target " + target);
                    View currentView = listView.getChildAt(currPosition); //current row

                    if( currentView != null){
                        Log.d(TAG, "view not null, set to DONE");
                        TextView status = (TextView) currentView.findViewById(R.id.phone_status);
                        status.setText("Download Status: Done");
                        //Already updated Text
                        if( currPosition < firstPosition)  m.setUpdateText(true);
                      //  Log.d(TAG, m.getCategory() + " updateText is " + m.getUpdateText()+ " positino is " + currPosition);
                    }

                    //Notification
                    //Create notification
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(getApplicationContext())
                                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                                    .setContentTitle("PhoneGallery")
                                    .setContentText(m.getCategory() + " has been downloaded");
                    // Sets an ID for the notification
                    int mNotificationId = Storage.notificationID.get(m.getCategory());
                    // Gets an instance of the NotificationManager service
                    NotificationManager mNotifyMgr =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    // Builds the notification and issues it.
                    // Builds the notification and issues it.
                    mNotifyMgr.notify(mNotificationId, mBuilder.build());
                }
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
