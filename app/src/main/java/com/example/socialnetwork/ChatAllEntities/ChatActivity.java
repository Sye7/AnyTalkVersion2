package com.example.socialnetwork.ChatAllEntities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.socialnetwork.AnyTalkPackage.CountryLists;
import com.example.socialnetwork.AnyTalkPackage.CountryModel;
import com.example.socialnetwork.CircularImage.CircleTransformation;
import com.example.socialnetwork.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ChatActivity extends AppCompatActivity {

    DatabaseReference reference;

    FirebaseUser firebaseUser;

    public static CountryModel countryModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = findViewById(R.id.toolbar_chat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Call");

        senderCountryName = findViewById(R.id.sender_country_name_text_view);
        senderFlag = findViewById(R.id.senderFlag);
        receiverCountryName = findViewById(R.id.receiver_country_name_text_view);
        receiverFlag = findViewById(R.id.receiverFlag);
        switchlang = findViewById(R.id.switchLang);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Profile");


        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new ChatsFragment(), "Contacts");
        viewPagerAdapter.addFragment(new UsersFragment(), "All Contacts");


        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        if(senderCountryModel != null)
        {
            senderCountryName.setText(senderCountryModel.getName());
            senderFlag.setImageResource(senderCountryModel.getFlag());
            updatedSender(senderCountryModel.getFlag());
        }
        else{
            updatedSender(R.mipmap.ca);
            senderCountryModel = new CountryModel("Canada",R.mipmap.ca,"US","en");

        }

         if(receiverCountryModel != null)
        {
            receiverCountryName.setText(receiverCountryModel.getName());
            receiverFlag.setImageResource(receiverCountryModel.getFlag());
            updatedReceiver(receiverCountryModel.getFlag());
        }
         else
         {
             updatedReceiver(R.mipmap.in );
             receiverCountryModel = new CountryModel("India",R.mipmap.in,"IN", "hi");
         }

    }

    int avtarsize = 88;

    public void updatedSender(int id) {

        Picasso.get()
                .load(id)
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(avtarsize, avtarsize)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(senderFlag);
    }


    public void updatedReceiver(int id) {

        Picasso.get()
                .load(id)
                .placeholder(R.drawable.img_circle_placeholder)
                .resize(avtarsize, avtarsize)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(receiverFlag);
    }




    ImageView senderFlag;
    TextView senderCountryName;


    ImageView receiverFlag;
    TextView receiverCountryName;
    ImageView switchlang;
    boolean flag = true;

    public void switchLang(View view) {

        if (flag) {

            senderCountryName.setText(receiverCountryName.getText().toString());
            senderFlag.setImageResource(R.mipmap.in);


            receiverCountryName.setText(senderCountryName.getText().toString());
            receiverFlag.setImageResource(R.mipmap.ca);
            flag = false;

        } else {

            senderCountryName.setText("English");
            senderFlag.setImageResource(R.mipmap.ca);


            receiverCountryName.setText("Hindi");
            receiverFlag.setImageResource(R.mipmap.in);
            flag = true;
        }


    }

   public static int isSender = -1;
    public static CountryModel senderCountryModel;
   public static CountryModel receiverCountryModel;

    public void openCountrySenderActivity(View view) {

        isSender = 1;
        startActivity(new Intent(getApplicationContext(), CountryLists.class));

    }

    public void openCountryReceiverActivity(View view) {

        isSender = 2;
        startActivity(new Intent(getApplicationContext(), CountryLists.class));

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ArrayList<Fragment> fragments;
        public ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();

        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);

        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}

