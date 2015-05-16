package com.vinako.phonegallery.gridview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.vinako.phonegallery.DetailActivity;
import com.vinako.phonegallery.MainActivity;
import com.vinako.phonegallery.R;
import com.vinako.phonegallery.storage.Storage;


public class GridViewActivity extends Activity {

    private GridView gridView;
    private ImageAdapter adapter;
    public static final  String TAG = "GridViewActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        gridView = (GridView) findViewById(R.id.gridview);
        adapter = new ImageAdapter(this, Storage.phones, Storage.selectedList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
//                Toast.makeText(GridViewActivity.this, "" + position,
//                        Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(getApplicationContext(), DetailActivity.class);
                String imageName = adapter.getImageName()[position];
                Log.d(TAG, "Image send to screen 3 is " + imageName);
                intent.putExtra("imageName", imageName);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_grid_view, menu);
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
