package it.crudmon.interview.topqueue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class CustomGridTopics extends RecyclerView.Adapter<CustomGridTopics.MyTopicHolder>
{
    public RecyclerView re;
    private ArrayList<TopicsModel> dataSet ;
   public Context context=null;
    VenueAdapterClickCallbacks venueAdapterClickCallbacks;

    public class MyTopicHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView subtitle;
        ImageView share;
        ImageView logo;
        public MyTopicHolder(View itemView)
        {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.topic_title);
            this.subtitle = (TextView) itemView.findViewById(R.id.subtitle);
            this.share=(ImageView)itemView.findViewById(R.id.share);
            this.logo=(ImageView)itemView.findViewById(R.id.img);
        }
    }
    public CustomGridTopics(Context c,ArrayList<TopicsModel> data, VenueAdapterClickCallbacks venueAdapterClickCallback)
    {

        this.dataSet = data;
        this.venueAdapterClickCallbacks=venueAdapterClickCallback;
        context=c;

    }
    @Override
    public MyTopicHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topics_branch_card, parent, false);
        MyTopicHolder myTopicHolder=new MyTopicHolder(view);
        re = (RecyclerView) parent.findViewById(R.id.topic_grid);
        return myTopicHolder;
    }

    @Override
    public void onBindViewHolder(MyTopicHolder holder,final int position)
    {
            TextView title = holder.title;
            TextView subtitle = holder.subtitle;
            ImageView share=holder.share;
            ImageView logo=holder.logo;
            title.setText(dataSet.get(position).getTitle());
        StringBuilder s=new StringBuilder("http://main.topqueue.in/");
        String p=dataSet.get(position).getFile_url();
        Log.d("Incomming URL ", "url  " + p);

        if(p!=null) {
            //Setting image
            s.append(p);
            setImageoncard(context,logo,s.toString());
        }
        subtitle.setText(dataSet.get(position).getSubtitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                venueAdapterClickCallbacks.onCardClick(dataSet.get(position).getTitle());

            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            venueAdapterClickCallbacks.onShareClick(dataSet.get(position).getTitle());
            }
        });

    }
    public interface VenueAdapterClickCallbacks {
        void onCardClick( String p);
        void onShareClick(String a);

    }

    @Override
    public int getItemCount()
    {
        return dataSet.size();
    }

    public void setFilter(List<TopicsModel> countryModels)
    {
        dataSet = new ArrayList<>();
        dataSet.addAll(countryModels);
        notifyDataSetChanged();
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

