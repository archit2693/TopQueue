package it.crudmon.interview.topqueue;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class CustomGridpref extends BaseAdapter{
    private Context mContext;
    private final String[] web;
    private final String[] subh;
    public ArrayList<String> arrayList;
    public ArrayList<Integer> state;

    CheckBox checkBox;
    private PrefManager pref;


    public CustomGridpref(Context c,String[] web,String[] subh ) {
        mContext = c;

        this.web = web;
        this.subh=subh;
    }

    @Override
    public int getCount() {

        return web.length;
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View grid;
        arrayList=new ArrayList<>();
        //taking state array from pref


        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.checked_field_card_preference, null);
            TextView textView1 = (TextView) grid.findViewById(R.id.title);
            TextView textView2=(TextView)grid.findViewById(R.id.subtitle);
            checkBox=(CheckBox)grid.findViewById(R.id.check_box);

            pref = new PrefManager(parent.getContext());
            textView1.setText(web[position]);
            textView2.setText(subh[position]);
            Log.d("prefchck",String.valueOf(pref.getpref()));

//if(pref.getPermanentFieldId()==0)
//{
    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            CheckboxArray object = new CheckboxArray();
            if (isChecked) {

                arrayList.add(web[position]);
                state.add(1);
                Toast.makeText(buttonView.getContext(), "ADDED: " + web[position], Toast.LENGTH_LONG).show();
                Gson gson = new Gson();
                String array = gson.toJson(arrayList);
                Log.d("array:  ", array);
                pref.setPermanentFieldId(1);
                pref.setPref(array);
                pref.setcheck(true);


            } else {
                if (arrayList.contains(web[position])) {
                    arrayList.remove(web[position]);
                    Toast.makeText(buttonView.getContext(), "Removed: " + web[position], Toast.LENGTH_LONG).show();
                    Gson gson = new Gson();
                    String array = gson.toJson(arrayList);
                    pref.setPermanentFieldId(1);
                    pref.setPref(array);
                    pref.setcheck(false);

                }
            }
        }
    });
//
//}
            //ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);          When Image to be added to cards
            //imageView.setImageResource(Imageid[position]);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}