package it.crudmon.interview.topqueue;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>
{
//    private int mOriginalHeight = 0;
//    private boolean mIsViewExpanded = false;
//    private int expandedPosition = -1;


    public static View view;
    public RecyclerView re;
    VenueAdapterClickCallbacks venueAdapterClickCallbacks;

Context mContext;
    private ArrayList<DataModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView question;
        TextView answer,code;
        ImageView share;
//        ImageView fav;

        LinearLayout llExpandArea;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.question = (TextView) itemView.findViewById(R.id.question_text);
            this.answer = (TextView) itemView.findViewById(R.id.answer_text);
            this.code = (TextView) itemView.findViewById(R.id.code_text);
            this.share=(ImageView)itemView.findViewById(R.id.share_ques);
//            this.fav=(ImageView)itemView.findViewById(R.id.fav);
            view=(View)itemView.findViewById(R.id.separater_view);
            this.llExpandArea = (LinearLayout) itemView.findViewById(R.id.llExpandArea);
            this.code.setVisibility(View.GONE);

        }
    }

    public CustomAdapter(Context mContext,ArrayList<DataModel> data,VenueAdapterClickCallbacks venueAdapterClickCallback) {
        this.dataSet = data;
        this.mContext = mContext;
        this.venueAdapterClickCallbacks=venueAdapterClickCallback;
    }

//    int[] b;

//    int[] a;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);
//        b=new int[getItemCount()];
//        Arrays.fill(b,0);
//        a=new int[getItemCount()];
//        Arrays.fill(a,0);
        re=(RecyclerView)parent.findViewById(R.id.recycler_view);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                MyViewHolder holder=(MyViewHolder)v.getTag();
//                int current=holder.getLayoutPosition();
//
//                Log.d("Layout POsition",String.valueOf(holder.getLayoutPosition()));
//                Log.d("Adapter POsition", String.valueOf(holder.getAdapterPosition()));
//                Log.d("Id ",String.valueOf(holder.getItemId()));
//
////                if(b[current]==0)
////                {
////                    holder.llExpandArea.setVisibility(View.VISIBLE);
////                    b[current]=1;
//////                    notifyItemChanged(current);
////                    re.scrollToPosition(current);
////
////                }
////                else
////                {
////                    holder.llExpandArea.setVisibility(View.GONE);
////                    b[current]=0;
////                    re.scrollToPosition(current);
////
////                }
//
//            }});

//        final ImageView fav=(ImageView)view.findViewById(R.id.fav);
//        fav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MyViewHolder holder=(MyViewHolder)view.getTag();
//                int current=holder.getLayoutPosition();
//                if(a[current]==0) {
//                    fav.setImageResource(R.drawable.star_filled);
//                    a[current]=1;
//
//                }
//                else {
//                    fav.setImageResource(R.drawable.star);
//                    a[current]=0;
//                }
//
//            }
//        });
        MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setTag(myViewHolder);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView question = holder.question;
        TextView answer = holder.answer;
        TextView code = holder.code;

        ImageView share=holder.share;
        code.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
        final int current=holder.getLayoutPosition();
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                venueAdapterClickCallbacks.onShareClick(dataSet.get(current).getQuestion());
            }
        });
        String q="<b><b> Question "+(listPosition+1)+" : </b></b>"+" "+dataSet.get(listPosition).getQuestion();
        String a="<b><b> Answer  : </b></b>"+" "+dataSet.get(listPosition).getAnswer();
        String c="<b><b> Code  : </b></b>"+" "+dataSet.get(listPosition).getCode();

        question.setText(Html.fromHtml(q));
        answer.setText(Html.fromHtml(a));
        String code_string=dataSet.get(listPosition).getCode();
        if(!code_string.equals("")) {
            code.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
            code.setText(Html.fromHtml(c));
        }
        if (dataSet.get(listPosition).isChecked == true) {
            holder.llExpandArea.setVisibility(View.VISIBLE);

//            holder.card.setCardBackgroundColor(Color.parseColor("#D3EABC"));
//            holder.addRemoveButtonImage.setImageResource(R.drawable.remove_customization_icon);
        } else {
            holder.llExpandArea.setVisibility(View.GONE);

//            holder.card.setCardBackgroundColor(Color.parseColor("#ffffff"));
//            holder.addRemoveButtonImage.setImageResource(R.drawable.add_customization_icon);
        }


        onExpand( holder.itemView, mContext, listPosition);


//        if(b[current]==0)
//        {
//            holder.llExpandArea.setVisibility(View.VISIBLE);
//            b[current]=1;
//            re.scrollToPosition(current);
//
//        }
//        Log.d("Current", String.valueOf(current) + "          " + String.valueOf(b[current]));
//        Log.d("check",String.valueOf(b[current]));
//        if(b[current]==0)
//        {
//            holder.llExpandArea.setVisibility(View.GONE);
//
//           // b[current]=0;
////            re.scrollToPosition(current);
//
//        }
//        if(b[current]==1)
//        {
//            holder.llExpandArea.setVisibility(View.VISIBLE);
//            // b[current]=0;
////            re.scrollToPosition(current);
//
//        }
////        if(a[current]==0)
//        {
//            fav.setImageResource(R.drawable.star);
//        }
//        if(a[current]==1)
//        {
//            fav.setImageResource(R.drawable.star_filled);
//        }
    }


    private void onExpand(View view, final Context mContext, final int position) {
        view.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dataSet.get(position).isChecked != true) {
                            dataSet.get(position).isChecked = true;
                            re.scrollToPosition(position);
                        }

                        else {
                            dataSet.get(position).isChecked = false;
                            re.scrollToPosition(position);
                        }
                        notifyItemChanged(position);

                    }
                }

        );
    }
    public interface VenueAdapterClickCallbacks {
        void onShareClick(String a);

    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }
    public void setFilter(List<DataModel> countryModels) {
        dataSet = new ArrayList<>();
        dataSet.addAll(countryModels);
         notifyDataSetChanged();
    }


}
