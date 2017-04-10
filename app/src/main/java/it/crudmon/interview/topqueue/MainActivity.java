package it.crudmon.interview.topqueue;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import model.popularList;
import model.recentList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout mDrawerLayout;
    Toolbar toolbar;
    HomeFragment home;
    LinearLayout linear;
    ArrayList<recentList> recentarrayList;
    ArrayList<popularList> populararrayList;
    PrefManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        //geting intent Extrasssssss
        Intent i=getIntent();
        String recent=i.getStringExtra("RecentList");
        String popular=i.getStringExtra("PopularList");

        //Using prefs

        prefManager =new PrefManager(getApplication());
        prefManager.setRecent(recent);
        prefManager.setPopular(popular);



        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Referring toolbar to add menu icons to it
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        linear=(LinearLayout)findViewById(R.id.linear);

                toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        //Setting up toolbar touch area for navigation drawer
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        FragmentManager manager= getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        home=new HomeFragment();
        transaction.add(R.id.linear, home).commit();

        NavigationView navigationView=(NavigationView)findViewById(R.id.navigationView);
//        View headerLayout = navigationView.inflateHeaderView(R.layout.drawer_header);
//        ImageView ivHeaderPhoto = (ImageView)headerLayout.findViewById(R.id.drawer_head);

        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager manager= getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        if(item.getItemId()==R.id.home)
        {
            if(linear.getChildAt(0).getId()!=R.id.frag_home)
            {  home=new HomeFragment();
            linear.removeAllViews();
                toolbar.setTitle("Home");
            transaction.add(R.id.linear, home).commit();}
        }
       else  if (item.getItemId()==R.id.brach)
        {


            linear.removeAllViews();
            toolbar.setTitle("Branches");
            transaction.add(R.id.linear, new BranchFragment()).commit();
        }
        else if(item.getItemId()==R.id.help)
        {


            linear.removeAllViews();
            toolbar.setTitle("Help");
            transaction.add(R.id.linear, new HelpFragment()).commit();

        }
        else if(item.getItemId()==R.id.login)
        {
            Intent i =new Intent(getApplicationContext(),ActivityForLoginFragment.class);
            startActivity(i);

        }
        else if(item.getItemId()==R.id.rate)
        {
            Intent i = new Intent(android.content.Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=it.crudmon.interview.topqueue"));
            startActivity(i);
        }

        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
