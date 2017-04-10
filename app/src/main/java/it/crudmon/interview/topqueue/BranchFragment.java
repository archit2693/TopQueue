package it.crudmon.interview.topqueue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import model.FieldsList;
import model.fields;
import model.recentList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import server.interfaces.API_Interface;

public class BranchFragment extends Fragment{
    GridView grid;
    GridView popularGrid;
    TextView recentText;
    TextView popularText;
    List<FieldsList> fieldsArrays;
    ProgressDialog dialog;
    private static PrefManager pref;
    Toolbar toolbar;
    CustomGridBranch adapter;
    LinearLayout linear;
    String[] test;
    String s1;
    String s;
    String url[];
    String[] web;
    String[] desc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       View view=inflater.inflate(R.layout.fields, container, false);
        grid=(GridView)view.findViewById(R.id.fieldGrid);
       // toolbar.setTitle("Branches");
        pref = new PrefManager(getActivity());
        linear=(LinearLayout)view.findViewById(R.id.field_l);
        dialog = ProgressDialog.show(getActivity(),null,"Please wait...", true);

        testData();




        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            FragmentManager fm = getFragmentManager();

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Toast.makeText(getActivity(), desc[position], Toast.LENGTH_SHORT).show();

                pref.setFieldId(+position + 1);
                startActivity(new Intent(getActivity(), BranchFieldFragment.class));


            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(dialog!=null)
            dialog.dismiss();
    }

    public void testData()
    {
        Gson gson=new Gson();
        String f=pref.getFields();
        fieldsArrays=new ArrayList<>();

        if(!f.isEmpty()) {
            Type type = new TypeToken<ArrayList<FieldsList>>() {
            }.getType();
            fieldsArrays = gson.fromJson(f, type);
        }
        test=new String[fieldsArrays.size()];
        web=new String[fieldsArrays.size()];
        desc=new String[fieldsArrays.size()];
        url=new String[fieldsArrays.size()];

        for (int i = 0; i < fieldsArrays.size() ; i++)
        {
            s = fieldsArrays.get(i).fieldName;
            s1= fieldsArrays.get(i).discription;
            url[i]=fieldsArrays.get(i).file_url;
            test[i] = s;
            desc[i]=s1;
            if(s1.length()>44)
                web[i]=s1.substring(0,45)+"....";
            else
                web[i]=s1;

        }

        adapter = new CustomGridBranch(getActivity(), test,web,url);
        dialog.dismiss();
        grid.setAdapter(adapter);

    }

}
