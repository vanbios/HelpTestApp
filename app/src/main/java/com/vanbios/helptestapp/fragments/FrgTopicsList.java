package com.vanbios.helptestapp.fragments;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vanbios.helptestapp.R;
import com.vanbios.helptestapp.adapters.QuestionsRecyclerAdapter;
import com.vanbios.helptestapp.objects.Item;
import com.vanbios.helptestapp.utils.InfoFromDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class FrgTopicsList extends CommonFragment {

    public final static String BROADCAST_FRG_ACCOUNT_ACTION = "com.androidcollider.easyfin.frgaccount.broadcast";
    public final static String PARAM_STATUS_FRG_ACCOUNT = "update_frg_account";
    public final static int STATUS_UPDATE_FRG_ACCOUNT = 4;

    private RecyclerView recyclerView;

    private LinkedHashMap<String, ArrayList<Item>> data;

    private TextView tvEmpty;

    private QuestionsRecyclerAdapter recyclerAdapter;

    private BroadcastReceiver broadcastReceiver;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_topics_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerTopics);
        tvEmpty = (TextView) view.findViewById(R.id.tvEmptyList);

        setRecycler();

        return view;
    }


    private void setRecycler() {

        data = InfoFromDB.getInstance().getDataSource().getData();

        //setVisibility();

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerAdapter = new QuestionsRecyclerAdapter(data, getActivity());
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void openFrgDetail(Item item) {
        FrgDetail frgDetail = new FrgDetail();
        Bundle arguments = new Bundle();
        arguments.putSerializable("item", item);
        frgDetail.setArguments(arguments);

        addFragment(frgDetail);
    }



    public void addFragment(Fragment f){
        treatFragment(f, true, false);
    }

    public void replaceFragment(Fragment f){
        treatFragment(f, false, true);
    }

    public Fragment getTopFragment(){
        return getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }

    private void treatFragment(Fragment f, boolean addToBackStack, boolean replace){
        String tag = f.getClass().getName();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

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

    @Override
    public String getTitle() {
        return getString(R.string.app_name);
    }

}
