package com.vanbios.helptestapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.vanbios.helptestapp.utils.ResultParser;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        setToolbar(getString(R.string.app_name));

        ResultParser.sendPostLoginReq(this);
    }

    private void initializeViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
    }

    private void setToolbar(String title) {

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {


            actionBar.setDisplayShowCustomEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_menu);
        }
    }







    public void addFragment(Fragment f){
        treatFragment(f, true, false);
    }

    public void replaceFragment(Fragment f){
        treatFragment(f, false, true);
    }

    public Fragment getTopFragment(){
        return getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }

    private void treatFragment(Fragment f, boolean addToBackStack, boolean replace){
        String tag = f.getClass().getName();
        FragmentTransaction ft =  getSupportFragmentManager().beginTransaction();

        if (replace) {

            ft.replace(R.id.fragment_container, f, tag);

        } else {

            Fragment currentTop = getTopFragment();

            if (currentTop != null) ft.hide(currentTop);

            ft.add(R.id.fragment_container, f, tag);
        }

        if (addToBackStack) ft.addToBackStack(tag);

        ft.commitAllowingStateLoss();
    }








}
