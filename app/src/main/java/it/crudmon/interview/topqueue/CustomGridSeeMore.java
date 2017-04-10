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

public class CustomGridSeeMore extends BaseAdapter{
    private Context mContext;
    private final String[] web;
    private final String[] url;



    public CustomGridSeeMore(Context c,String[] web,String[] url ) {
        mContext = c;

        this.web = web;
        this.url= url;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.cards_in_grid, null);
            TextView textView = (TextView) grid.findViewById(R.id.textView);
            textView.setText(web[position]);
            ImageView imageView=(ImageView)grid.findViewById(R.id.home_image);




                StringBuilder s = new StringBuilder("http://main.topqueue.in/");
                String p = url[position];
                Log.d("Incomming URL ", "url  " + p);

                if (p != null) {
                    //Setting image
                    s.append(p);
                    setImageoncard(mContext, imageView, s.toString());
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