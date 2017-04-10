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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import model.home;
import model.popularList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import server.interfaces.API_Interface;


public class SeeMorePopularActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private static CustomAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;
    Context context;
    ImageView im1,im2;
    ArrayList<popularList> populararrayList;
    private static PrefManager pref;
    static View.OnClickListener myOnClickListener;
    public String topic;
    ProgressDialog dialog;
    //public SearchView search;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_recycleview);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context=this;
        //FontAwesome
//        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
//        FontManager.markAsIconContainer(findViewById(R.id.icons_container), iconFont);


        pref = new PrefManager(getApplicationContext());
        Intent i=getIntent();
        topic=i.getStringExtra("topic");
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Questions");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // search = (SearchView) findViewById( R.id.search);
        im1=(ImageView)findViewById(R.id.im1);
        im2=(ImageView)findViewById(R.id.im2);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        //Data
        Gson gson=new Gson();
        String recent1=pref.getPopular();
        if(!recent1.isEmpty()) {
            Type type = new TypeToken<ArrayList<popularList>>() {}.getType();
            populararrayList = gson.fromJson(recent1, type);
            Log.d("IS WORKINGG",populararrayList.get(0).topic_name);
        }


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
                Log.d("staste:  ",String.valueOf(newState));
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

//        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if(scrollY-oldScrollY>0)
//                    im2.setVisibility(View.VISIBLE);
//                if(scrollY-oldScrollY<0)
//                    im1.setVisibility(View.VISIBLE);
//
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(this);
//        MenuItemCompat.setOnActionExpandListener(item,
//                new MenuItemCompat.OnActionExpandListener() {
//                    @Override
//                    public boolean onMenuItemActionCollapse(MenuItem item) {
//                        // Do something when collapsed
//                        adapter.setFilter(data);
//                        return true; // Return true to collapse action view
//                    }
//
//                    @Override
//                    public boolean onMenuItemActionExpand(MenuItem item) {
//                        // Do something when expanded
//                        return true; // Return true to expand action view
//                    }
//                });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<DataModel> filteredModelList = filter(data, newText);
        adapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
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

    //Adding data to arrayList from JSON api
    public void dataQAnswer()
    {
        data=new ArrayList<DataModel>();
        String q,a,c;
        for (int j = 0; j < populararrayList.size() ; j++)
        {
            // Log.d("topic:",response.body().hot.recentList.get(j).topic_name);
            String name = populararrayList.get(j).topic_name;
            String s=pref.getName();
            Log.d("msg",name);
            if (name.equals(s))
            {
                for (int i = 0; i < populararrayList.get(j).questionlist.size(); i++)
                {
                    q = populararrayList.get(j).questionlist.get(i).question;
                    a = populararrayList.get(j).questionlist.get(i).answer;
                    c = populararrayList.get(j).questionlist.get(i).code;
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
                    String ques=a.split(":")[1];
                    String shareBody = "Hey check this question in TopQueue-Interview Questions.\n"+ques+" \n\nDownload this app @ https://play.google.com/store/apps/details?id=it.crudmon.interview.topqueue";
                    // sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                }
            });
            recyclerView.setAdapter(adapter);


        }

    }

}
