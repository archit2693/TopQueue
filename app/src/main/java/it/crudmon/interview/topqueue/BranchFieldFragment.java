package it.crudmon.interview.topqueue;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import model.FieldsList;
import model.modelsTopic;
import model.modelsTopicList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import server.interfaces.API_Interface;

/**
 * Created by this pc on 02-04-16.
 */
public class BranchFieldFragment extends AppCompatActivity implements SearchView.OnQueryTextListener{
    GridView grid;
    GridView popularGrid;
    TextView recentText;
    TextView popularText;
    Toolbar toolbar;
    ProgressDialog dialog;
    private RecyclerView.LayoutManager layoutManager;
    CustomGridTopics adapter;

    RecyclerView recyclerView;
    ArrayList<String> web;
    ArrayList<modelsTopicList> modelsTopicLists;
    ArrayList<TopicsModel> topicsModels;
    private PrefManager pref;
    Context c;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return true;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topics);
        c=this;
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        recyclerView=(RecyclerView)findViewById(R.id.topic_grid);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        pref = new PrefManager(getApplicationContext());
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Topics");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dialog = ProgressDialog.show(this,null,"Please wait...", true);
        testData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<TopicsModel> filteredModelList = filter(topicsModels, newText);
        adapter.setFilter(filteredModelList);
        return true;
    }
    private List<TopicsModel> filter(List<TopicsModel> topics, String query) {
        query = query.toLowerCase();

        final List<TopicsModel> filteredModelList = new ArrayList<>();
        for (TopicsModel topic : topics) {
            final String text = topic.getTitle().toLowerCase();
            if (text.contains(query)) {
                // model.question= model.question.replace(query,"<b><font color=#2825A6>"+ query+ "</font></b>");


                filteredModelList.add(topic);
            }

        }
        return filteredModelList;
    }

    public void testData()
    {

        Gson gson=new Gson();
        String f=pref.getBranches();
        modelsTopicLists=new ArrayList<modelsTopicList>();
        topicsModels=new ArrayList<TopicsModel>();

        String s;
        if(!f.isEmpty()) {
            Type type = new TypeToken<ArrayList<modelsTopicList>>() {
            }.getType();
            modelsTopicLists = gson.fromJson(f, type);
        }

        web =new ArrayList<String>();
        for (int i = 0; i < modelsTopicLists.size() ; i++)
        {
            int fid=Integer.parseInt(modelsTopicLists.get(i).field_id);
            int o=pref.getFieldId();
            if(fid==o)
            {
                s = modelsTopicLists.get(i).topic_name;
                String s1=modelsTopicLists.get(i).description;
                String file_url=modelsTopicLists.get(i).file_url;
                TopicsModel t=new TopicsModel(s,s1,file_url);
                topicsModels.add(t);

            }
        }
        adapter=new CustomGridTopics(c, topicsModels, new CustomGridTopics.VenueAdapterClickCallbacks() {
                    @Override
                    public void onCardClick(String p) {

                        pref.setClass(p);
                        startActivity(new Intent(c,BranchQuestionActivity.class));

                    }

                    @Override
                    public void onShareClick(String a) {
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = "Check out "+a+" questions in TopQueue-Interview Questions. You can find many more topics of different fields... \nDownload this app @ https://play.google.com/store/apps/details?id=it.crudmon.interview.topqueue";
                       // sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share via"));
                    }
                });
                dialog.dismiss();
                recyclerView.setAdapter(adapter);
    }

}
