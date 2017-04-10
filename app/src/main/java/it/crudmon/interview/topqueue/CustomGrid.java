package it.crudmon.interview.topqueue;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomGrid extends BaseAdapter{
    private Context mContext;
    String URL_TOPQUEUE_BASE = "http://main.topqueue.in";
    private final ArrayList<String> web;
    ArrayList<String> url;

    public CustomGrid(Context c,ArrayList<String> web,ArrayList<String> url ) {
        mContext = c;
        this.url=url;
        this.web = web;
    }

    @Override
    public int getCount() {

        return web.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.cards_in_grid, null);
            TextView textView = (TextView) grid.findViewById(R.id.textView);

            ImageView imageView=(ImageView)grid.findViewById(R.id.home_image);
            textView.setText(web.get(position));


            if(!web.get(position).equals("See More")) {
                StringBuilder s = new StringBuilder("http://main.topqueue.in/");
                String p = url.get(position);
                Log.d("Incomming URL ", "url  " + p);

                if (p != null) {
                    //Setting image
                    s.append(p);
                    setImageoncard(mContext, imageView, s.toString());
                }
            }else
            {
                imageView.setImageResource(R.drawable.seemore);
            }

            //ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);          When Image to be added to cards
            //imageView.setImageResource(Imageid[position]);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
    private void setImageoncard(Context context, ImageView img, String url) {
        Picasso.with(context)
                .load(url)
                .fit()
                .centerCrop()
//                .placeholder(R.drawable.placeholder_cherish)
//                .placeholder(R.drawable.placeholder_cherish)
                .into(img);
    }
}