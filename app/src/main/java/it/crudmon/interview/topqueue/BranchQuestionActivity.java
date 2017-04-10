package it.crudmon.interview.topqueue;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import model.modelsTopic;
import model.modelsTopicList;
import model.recentList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import server.interfaces.API_Interface;


public class BranchQuestionActivity extends AppCompatActivity  implements SearchView.OnQueryTextListener{

    private static CustomAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private PrefManager pref;
    private static ArrayList<DataModel> data;
    ArrayList<modelsTopicList> modelsTopicLists;
    ImageView im1,im2;
    static View.OnClickListener myOnClickListener;
   Context context;
    ProgressDialog dialog;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_recycleview);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        pref = new PrefManager(getApplicationContext());
        Intent i=getIntent();
        context=this;
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Questions");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        im1=(ImageView)findViewById(R.id.im1);
        im2=(ImageView)findViewById(R.id.im2);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //dialog = ProgressDialog.show(this,null,"Please wait...", true);
        dataQAnswer();
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(0);

            }
        });
        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.smoothScrollToPosition(data.size() - 1);


            }
        });


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy<0) {
                    im1.setVisibility(View.VISIBLE);
                    im2.setVisibility(View.GONE);

                    new CountDownTimer(1500,1)
                    {
                        @Override
                        public void onFinish() {
                            im1.setVisibility(View.GONE);

                        }

                        @Override
                        public void onTick(long millisUntilFinished) {

                        }
                    }.start();

                }
                if(dy>0) {
                    im1.setVisibility(View.GONE);
                    im2.setVisibility(View.VISIBLE);
                    new CountDownTimer(1500,1)
                    {
                        @Override
                        public void onFinish() {
                            im2.setVisibility(View.GONE);

                        }

                        @Override
                        public void onTick(long millisUntilFinished) {

                        }
                    }.start();

                }

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

    //Adding data to arrayList from JSON api
    public void dataQAnswer()
    {
        data=new ArrayList<DataModel>();

        //Data
        Gson gson=new Gson();
        String b=pref.getBranches();
        if(!b.isEmpty()) {
            Type type = new TypeToken<ArrayList<modelsTopicList>>() {}.getType();
            modelsTopicLists = gson.fromJson(b, type);
            Log.d("IS WORKINGG",modelsTopicLists.get(0).topic_name);
        }
        String q,a,c;
        for (int j = 0; j < modelsTopicLists.size() ; j++)
        {
            // Log.d("topic:",response.body().hot.recentList.get(j).topic_name);
            String name = modelsTopicLists.get(j).topic_name;
            String s=pref.getName();
            Log.d("msg",name);
            if (name.equals(s))
            {
                for (int i = 0; i < modelsTopicLists.get(j).questionlist.size(); i++)
                {
                    q = modelsTopicLists.get(j).questionlist.get(i).question;
                    a = modelsTopicLists.get(j).questionlist.get(i).answer;
                    c = modelsTopicLists.get(j).questionlist.get(i).code;
//                    q = "Question " +(i + 1) + " : " + q;
//                    a = "Answer:   " + a;
//                    c = "Code:   " + c;
                    DataModel d = new DataModel(a, c, q);
                    data.add(d);
                }
            }

            adapter = new CustomAdapter(context,data, new CustomAdapter.VenueAdapterClickCallbacks() {
                @Override
                public void onShareClick(String a) {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    String shareBody = "Hey check this question in TopQueue-Interview Questions.\n"+a+" \n\nDownload this app @ https://play.google.com/store/apps/details?id=it.crudmon.interview.topqueue";
                    // sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }
            });
            recyclerView.setAdapter(adapter);


        }

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
        final List<DataModel> filteredModelList = filter(data, newText);
        adapter.setFilter(filteredModelList);
        return true;
    }
    private List<DataModel> filter(List<DataModel> models, String query) {
        query = query.toLowerCase();

        final List<DataModel> filteredModelList = new ArrayList<>();
        for (DataModel model : models) {
            final String text = model.getQuestion().toLowerCase();
            if (text.contains(query)) {
                // model.question= model.question.replace(query,"<b><font color=#2825A6>"+ query+ "</font></b>");


                filteredModelList.add(model);
            }

        }
        return filteredModelList;
    }
    // Onclick ListenerClass

}
