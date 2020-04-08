package com.example.socialnetwork.AnyTalkPackage;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetwork.R;

import java.util.ArrayList;
import java.util.Collections;

public class CountryLists extends AppCompatActivity implements LanguageListAdapter.ItemClickListener {

    LanguageListAdapter adapter;
    ArrayList<CountryModel> animalNames;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_lists);


        // data to populate the RecyclerView with
        animalNames = new ArrayList<>();

        CountryModel c = new CountryModel("English", R.mipmap.us, "US", "en");
        animalNames.add(c);

        c = new CountryModel("Hindi", R.mipmap.in, "IN", "hi");
        animalNames.add(c);

        c = new CountryModel("Arabic", R.mipmap.ae, "SA", "ar");
        animalNames.add(c);


        c = new CountryModel("Bengali", R.mipmap.in, "IN", "bn");
        animalNames.add(c);

        c = new CountryModel("Vietnamese", R.mipmap.vn, "US", "vi");
        animalNames.add(c);


        c = new CountryModel("JAPAN", R.mipmap.jp, "JP", "ja");
        animalNames.add(c);

        c = new CountryModel("Gujarati", R.mipmap.in, "IN", "gu");
        animalNames.add(c);


        c = new CountryModel("Italian", R.mipmap.it, "IN", "it");
        animalNames.add(c);

        c = new CountryModel("Spanish", R.mipmap.es, "ES", "es");
        animalNames.add(c);


        c = new CountryModel("Kannada", R.mipmap.in, "IN", "kn");
        animalNames.add(c);

        c = new CountryModel("Chinese", R.mipmap.cn, "CN", "zh");
        animalNames.add(c);

        c = new CountryModel("Korean", R.mipmap.kr, "KR", "ko");
        animalNames.add(c);

        c = new CountryModel("French", R.mipmap.fr, "FR", "fr");
        animalNames.add(c);


        c = new CountryModel("Turkish", R.mipmap.tr, "TR", "tr");
        animalNames.add(c);

        c = new CountryModel("Telugu", R.mipmap.in, "IN", "te");
        animalNames.add(c);

        c = new CountryModel("Tamil", R.mipmap.in, "IN", "ta");
        animalNames.add(c);

        c = new CountryModel("Russian", R.mipmap.ru, "RU", "ru");
        animalNames.add(c);


        c = new CountryModel("Romanian", R.mipmap.ro, "RO", "ro");
        animalNames.add(c);

        c = new CountryModel("Persian", R.mipmap.pr, "IR", "fa");
        animalNames.add(c);

        c = new CountryModel("Nepali", R.mipmap.np, "NP", "ne");
        animalNames.add(c);

        c = new CountryModel("German", R.mipmap.de, "DE", "de");
        animalNames.add(c);

        c = new CountryModel("Mongolian", R.mipmap.mn, "MN", "mn");
        animalNames.add(c);

        c = new CountryModel("Marathi", R.mipmap.in, "IN", "mr");
        animalNames.add(c);

        c = new CountryModel("Malayalam", R.mipmap.in, "IN", "ml");
        animalNames.add(c);

        Collections.sort(animalNames, (CountryModel o1, CountryModel o2) ->  o1.getName().compareTo(o2.getName()));

        // set up the RecyclerView
        recyclerView = findViewById(R.id.rv_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LanguageListAdapter(this, animalNames);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }

        };


    }

    @Override
    public void onItemClick(View view, int position) {


    }
}
