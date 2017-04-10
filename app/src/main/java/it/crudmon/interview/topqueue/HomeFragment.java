package it.crudmon.interview.topqueue;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

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
public class HomeFragment extends Fragment
{
    GridView grid;
    GridView popularGrid;
    TextView recentText;
    TextView popularText;
    private static PrefManager pref;
    ProgressDialog dialog;
    ImageView homeImage;
    Context con;
    String s1;
    CustomGrid adapter;
    CustomGrid adapter1;
    Set<String> al;
    int c;





    String[] web = {"Java","Java","Java","Java","Java","See More"};
    ArrayList<String> test=new ArrayList<>();
    ArrayList<String> test1=new ArrayList<>();
    ArrayList<String> url=new ArrayList<>();
    ArrayList<String> url1=new ArrayList<>();
    ArrayList<recentList> recentarrayList;
    ArrayList<popularList> populararrayList;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onPause() {
        super.onPause();
        if(dialog!=null)
            dialog.dismiss();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        homeImage=(ImageView)container.findViewById(R.id.home_image);
        //FontAwesome
//
//        Typeface font = Typeface.createFromAsset( getActivity().getAssets(), "fontawesome-webfont.ttf" );
//       // Typeface iconFont = FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME);
//        FontManager.markAsIconContainer(container.findViewById(R.id.home_image), font);

        con=getActivity();
        recentText=(TextView)view.findViewById(R.id.recent);
        popularText=(TextView)view.findViewById(R.id.popularText);

        // CustomGrid adapter = new CustomGrid(MainActivity.this, test);  Testing in AsyncTask
        grid=(GridView)view.findViewById(R.id.recentGrid);
        popularGrid=(GridView)view.findViewById(R.id.popularGrid);
        pref = new PrefManager(getActivity());
        dialog = ProgressDialog.show(getActivity(),null,"Please wait...", true);

        String recent1=pref.getRecent();
        String popular1=pref.getPopular();

        // From string to Json
        Gson gson=new Gson();
        if(!recent1.isEmpty()) {
            Type type = new TypeToken<ArrayList<recentList>>() {}.getType();
            recentarrayList = gson.fromJson(recent1, type);
            Log.d("IS WORKINGG",recentarrayList.get(0).topic_name);
        }
        if(!popular1.isEmpty()) {
            Type type = new TypeToken<ArrayList<popularList>>() {}.getType();
            populararrayList = gson.fromJson(popular1, type);
            Log.d("IS WORKINGG",populararrayList.get(0).topic_name);
        }
        // Creating Data and populating
        testData();

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                switch (test.get(+position))
                {
                    case "See More":
                        Intent i5=new Intent(getActivity(),SeeMoreRecent.class);
                        i5.putExtra("SeeMoreRecent",pref.getRecent());
                        startActivity(i5);
                        break;
                   default:
                       pref.setName(test.get(+position));
                       Intent i=new Intent(getActivity(),QuestionActivity.class);
                       i.putExtra("QuestionRecent",pref.getRecent());
                       startActivity(i);

                }
            }
        });

        popularGrid.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                         switch (test1.get(+position))
                        {
                            case "See More":
                                Intent i5=new Intent(getActivity(),SeeMorePopular.class);
                                i5.putExtra("SeeMorePopular",pref.getPopular());
                                startActivity(i5);
                                break;
                            default:
                                pref.setName(test1.get(+position));
                                Intent i=new Intent(getActivity(),PopularQuestionActivity.class);
                                i.putExtra("QuestionPopular",pref.getPopular());
                                startActivity(i);

                        }


                    }
                });
        return view;
    }




    public void testData()
    {
        for (int i = 0; i <5 ; i++)
        {
            test.add(recentarrayList.get(i).topic_name);
            url.add(recentarrayList.get(i).file_url);
            url1.add(populararrayList.get(i).file_url);
            test1.add(populararrayList.get(i).topic_name);
        }
        test.add("See More");
        test1.add("See More");
        adapter=new CustomGrid(getActivity(),test,url);
        adapter1=new CustomGrid(getActivity(),test1,url1);
        recentText.setVisibility(View.VISIBLE);
        popularText.setVisibility(View.VISIBLE);
        grid.setAdapter(adapter);
        popularGrid.setAdapter(adapter1);
        dialog.dismiss();
    }

}