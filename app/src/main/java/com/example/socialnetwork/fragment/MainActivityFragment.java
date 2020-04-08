package com.example.socialnetwork.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.socialnetwork.ChatAllEntities.ChatActivity;
import com.example.socialnetwork.R;

public class MainActivityFragment extends Fragment  {



    Context thisContext;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_main, container, false);
        thisContext = inflater.getContext();

        button = view.findViewById(R.id.homeButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(thisContext, ChatActivity.class));
            }
        });




        return view;
    }


}
