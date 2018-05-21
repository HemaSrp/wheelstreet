package com.wheelstreet.wheelstreet.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.wheelstreet.wheelstreet.R;
import com.wheelstreet.wheelstreet.fragment.ChatFragment;
import com.wheelstreet.wheelstreet.fragment.ProfileFragment;
import com.wheelstreet.wheelstreet.fragment.UpdateQuestionsFragment;

public class MainActivity extends AppCompatActivity implements ChatFragment.RegenerateArrayButtonClick {

    private Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>Chat</font>"));
                    fragment = ChatFragment.newInstance();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_notifications:
                    actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>Profile</font>"));
                    fragment = ProfileFragment.newInstance();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
          actionBar= getSupportActionBar();
        fragment = ChatFragment.newInstance();
        loadFragment(fragment);
        actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>Chat</font>"));

    }

    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }


    @Override
    public void onClickButton() {
        actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>Question & Answer</font>"));
        fragment = UpdateQuestionsFragment.newInstance();
        loadFragment(fragment);
    }
}
