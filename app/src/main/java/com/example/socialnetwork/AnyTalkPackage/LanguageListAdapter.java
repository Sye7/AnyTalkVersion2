package com.example.socialnetwork.AnyTalkPackage;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetwork.ChatAllEntities.ChatActivity;
import com.example.socialnetwork.R;

import java.util.List;

public class LanguageListAdapter extends RecyclerView.Adapter<LanguageListAdapter.ViewHolder> {


    List<CountryModel> listCountry;
    private LayoutInflater mInflater;


    // Best
    private ItemClickListener mClickListener;


    public LanguageListAdapter(Context context, List<CountryModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.listCountry = data;
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_language, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {



        CountryModel model = listCountry.get(position);

        holder.name.setText(model.getName());
        holder.countrySmallName.setText(model.getNameSmall());
        holder.flag.setImageResource(model.getFlag());

        holder.langFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ChatActivity.isSender == 1)
                {
                    ChatActivity.senderCountryModel = listCountry.get(position);
                    Intent intent = new Intent(mInflater.getContext(), ChatActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mInflater.getContext().startActivity(intent);
                    ((Activity)mInflater.getContext()).finish();
                }
                else if( ChatActivity.isSender == 2)
                {

                    ChatActivity.receiverCountryModel = listCountry.get(position);
                    Intent intent = new Intent(mInflater.getContext(), ChatActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mInflater.getContext().startActivity(intent);
                    ((Activity)mInflater.getContext()).finish();
                }


            }
        });

    }




    @Override
    public int getItemCount() {
        return listCountry.size();
    }

    // stores and recycles views as they are scrolled off screen
    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView countrySmallName;
        ImageView flag;
        FrameLayout langFrame;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.country_name_text_view_ll);
            countrySmallName = itemView.findViewById(R.id.country_name_text_view_ss);
            flag = itemView.findViewById(R.id.iv_flag);
            langFrame = itemView.findViewById(R.id.langFrame);


        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());

        }
    }

    // convenience method for getting data at click position
    CountryModel getItem(int id) {
        return listCountry.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
