package com.vanbios.helptestapp.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
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
import java.util.LinkedHashMap;


public class FrgTopicsList extends CommonFragment {

    public final static String BROADCAST_FRG_TOPICS_ACTION = "com.vanbios.helptestapp.fragments.frgtopicslist.broadcast";
    public final static String PARAM_STATUS_FRG_TOPICS = "update_frg_topics";
    public final static int STATUS_UPDATE_FRG_TOPICS = 1;

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

        makeBroadcastReceiver();

        return view;
    }

    private void setRecycler() {

        data = InfoFromDB.getInstance().getDataSrc().getData();

        setVisibility();

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerAdapter = new QuestionsRecyclerAdapter(data, getActivity());
        recyclerView.setAdapter(recyclerAdapter);
    }


    private void setVisibility() {
        if (data.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void makeBroadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int status = intent.getIntExtra(PARAM_STATUS_FRG_TOPICS, 0);

                if (status == STATUS_UPDATE_FRG_TOPICS) {

                    data.clear();

                    LinkedHashMap<String, ArrayList<Item>> result = InfoFromDB.getInstance().getDataSrc().getData();
                    data.putAll(result);

                    setVisibility();

                    recyclerAdapter = new QuestionsRecyclerAdapter(data, getActivity());
                    recyclerView.setAdapter(recyclerAdapter);
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter(BROADCAST_FRG_TOPICS_ACTION);

        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public String getTitle() {
        return getString(R.string.app_name);
    }

}
