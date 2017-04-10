package it.crudmon.interview.topqueue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import model.home;
import model.popularList;
import model.recentList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import server.interfaces.API_Interface;

/**
 * Created by this pc on 02-04-16.
 */
public class SeeMoreRecent extends AppCompatActivity
{

    Toolbar toolbar;
    GridView seemoregrid;
    GridView popularGrid;
    TextView recentText;
    TextView popularText;
    ProgressDialog dialog;
    private static PrefManager pref;

    CustomGridSeeMore adapter;
    String[] test; String[] url;
    ArrayList<recentList> recentarrayList;


    String[] web = {"Java","Java","Java","Java","Java","See More"};

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seemore);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Referring toolbar to add menu icons to it
        seemoregrid=(GridView)findViewById(R.id.seemoreGrid);

        pref = new PrefManager(getApplicationContext());
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Recent Topics");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        dialog = ProgressDialog.show(SeeMoreRecent.this, null, "Please wait...", true);
        Gson gson=new Gson();
        String recent1=pref.getRecent();
        if(!recent1.isEmpty()) {
            Type type = new TypeToken<ArrayList<recentList>>() {}.getType();
            recentarrayList = gson.fromJson(recent1, type);
            Log.d("IS WORKINGG",recentarrayList.get(0).topic_name);
        }
        testData();
        seemoregrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pref.setName(test[position]);
                startActivity(new Intent(getApplicationContext(), SeeMoreRecentActivity.class));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return true;
    }
    public void testData()
    {
        test=new String[recentarrayList.size()];
        url=new String[recentarrayList.size()];
        for (int i = 0; i <recentarrayList.size() ; i++)
        {
            test[i]=(recentarrayList.get(i).topic_name);
            url[i]=(recentarrayList.get(i).file_url);

        }
        adapter=new CustomGridSeeMore(getApplicationContext(),test,url);
        seemoregrid.setAdapter(adapter);

    }
}
