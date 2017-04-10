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
public class SeeMorePopular extends AppCompatActivity {
    Toolbar toolbar;
    GridView grid;
    GridView seemorepGrid;

    ArrayList<popularList> populararrayList;
    private static PrefManager pref;
    ProgressDialog dialog;

    CustomGridSeeMore adapter;


    String[] test;String[] url;

    String[] web = {"Java","Java","Java","Java","Java","See More"};

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seemore);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        seemorepGrid=(GridView)findViewById(R.id.seemoreGrid);

        pref = new PrefManager(getApplicationContext());
        //Referring toolbar to add menu icons to it
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Popular Topics");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
//        dialog = ProgressDialog.show(SeeMorePopular.this,null,"Please wait...", true);
        Gson gson=new Gson();
        String recent1=pref.getPopular();
        if(!recent1.isEmpty()) {
            Type type = new TypeToken<ArrayList<popularList>>() {}.getType();
            populararrayList = gson.fromJson(recent1, type);
            Log.d("IS WORKINGG",populararrayList.get(0).topic_name);
        }
        testData();
      seemorepGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              pref.setName(test[position]);
              startActivity(new Intent(getApplicationContext(), SeeMorePopularActivity.class));
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
        test=new String[populararrayList.size()];
        url=new String[populararrayList.size()];
        for (int i = 0; i <populararrayList.size() ; i++)
        {
            test[i]=(populararrayList.get(i).topic_name);
            url[i]=(populararrayList.get(i).file_url);


        }
        adapter=new CustomGridSeeMore(getApplicationContext(),test,url);
        seemorepGrid.setAdapter(adapter);

    }
}
