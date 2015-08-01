package com.vanbios.helptestapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class CommonFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    public abstract String getTitle();

}
