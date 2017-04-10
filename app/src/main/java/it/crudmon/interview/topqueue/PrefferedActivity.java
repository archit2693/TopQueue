package it.crudmon.interview.topqueue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import model.fields;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import server.interfaces.API_Interface;

/**
 * Created by this pc on 02-04-16.
 */
public class PrefferedActivity extends AppCompatActivity {
    GridView grid;
    GridView popularGrid;
    TextView recentText;
    TextView popularText;
    CheckboxArray checkboxArray;
    Toolbar toolbar;
    ProgressDialog dialog;
    CardView cd;
    CustomGridpref adapter;
  //  CheckBox chkIos;
    LinearLayout linearLayout;
   String ColorStatus;

    ArrayList<Integer>  arrayList;
    private PrefManager pref;
    HomeFragment fragment=new HomeFragment();
    FragmentManager fm=getSupportFragmentManager();


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {

            startActivity(new Intent(this,MainActivity.class));
        }
        return true;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        grid=(GridView)findViewById(R.id.prefGrid);
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.pref);
        pref = new PrefManager(getApplicationContext());
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        cd=(CardView)findViewById(R.id.pref_card_view);
        toolbar.setTitle("Preferences");
        arrayList=new ArrayList<Integer>();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        chkIos=(CheckBox)findViewById(R.id.check_box);
         dialog = ProgressDialog.show(this,null,"Please wait...", true);
        testData();









        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Toast.makeText(getApplicationContext(), "You just got fucked bro", Toast.LENGTH_SHORT).show();


//                    ColorDrawable gridColor = (ColorDrawable) view.getBackground();
//                    Log.d("status",String.valueOf(gridColor.getColor()));
//                    Log.d("status",String.valueOf(Color.GREEN));
//                    if(gridColor.getColor()!=Color.GREEN)
//                    {view.setBackgroundColor(Color.GREEN);
//                        view.setElevation(25);}
//                    else
//                    {view.setBackgroundColor(Color.WHITE);}
//


            }
        });

    }






    public void testData()
    {

        API_Interface.Factory.getInstance().getfields().enqueue(new Callback<fields>() {
            @Override
            public void onResponse(Call<fields> call, Response<fields> response) {
                String s = "Fail";
                String[] web =new String[response.body().fieldsArray.size()];
                String[] test= new String[response.body().fieldsArray.size()];

                for (int i = 0; i < response.body().fieldsArray.size() ; i++) {
                    s = response.body().fieldsArray.get(i).fieldName;
                    String s1=response.body().fieldsArray.get(i).discription;
                    test[i] = s;
                    if(s1.length()>30)
                        web[i]=s1.substring(0,29)+"....";
                    else
                        web[i]=s1;

                }

                adapter = new CustomGridpref(getApplicationContext(), test,web);
                dialog.dismiss();


                grid.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<fields> call, Throwable t) {
                Log.e("Failed", t.getMessage());
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
