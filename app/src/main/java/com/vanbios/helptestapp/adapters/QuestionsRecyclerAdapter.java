package com.vanbios.helptestapp.adapters;


import android.content.Context;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vanbios.helptestapp.MainActivity;
import com.vanbios.helptestapp.R;
import com.vanbios.helptestapp.fragments.FrgDetail;
import com.vanbios.helptestapp.objects.Item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class QuestionsRecyclerAdapter extends RecyclerView.Adapter<QuestionsRecyclerAdapter.MainViewHolder> {

    private ArrayList<Integer> headersPosList, questionsPosList;
    private ArrayList<Item> questionsList;
    private ArrayList<String> headersList;
    private int count = 0;

    private Context context;

    private final static int TYPE_HEADER = 0, TYPE_QUESTION = 1;


    public QuestionsRecyclerAdapter(LinkedHashMap<String, ArrayList<Item>> data, Context context) {

        this.context = context;

        Iterator iterator = data.entrySet().iterator();
        questionsList = new ArrayList<>();
        headersList = new ArrayList<>();
        headersPosList = new ArrayList<>();
        questionsPosList = new ArrayList<>();

        while (iterator.hasNext()) {

            Map.Entry pair = (Map.Entry) iterator.next();

            String header = (String) pair.getKey();
            headersList.add(header);
            headersPosList.add(count);
            count++;

            ArrayList<Item> itList = new ArrayList<>();
            itList.addAll((ArrayList<Item>) pair.getValue());

            if (!itList.isEmpty()) {

                for (Item item : itList) {
                    questionsList.add(item);
                    questionsPosList.add(count);
                    count++;
                }
            }
        }
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_HEADER:
                return new ViewHolderItemHeader(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false));
            case TYPE_QUESTION:
                return new ViewHolderItemQuestion(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

        if (holder instanceof ViewHolderItemHeader) {

            final String URL_IMAGE_1 = "http://504080.com/img/help/account_management.png";
            final String URL_IMAGE_2 = "http://504080.com/img/help/companies.png";

            ImageLoader imageLoader = ImageLoader.getInstance();
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .cacheOnDisc(true).resetViewBeforeLoading(true)
                    .build();

            for (int i = 0; i < headersPosList.size(); i++) {
                if (headersPosList.get(i) == position) {
                    String head = headersList.get(i);
                    ((ViewHolderItemHeader) holder).tvHead.setText(head);

                    if (i == 0) {
                        imageLoader.displayImage(URL_IMAGE_1, ((ViewHolderItemHeader) holder).ivHead, options);
                    } else {
                        imageLoader.displayImage(URL_IMAGE_2, ((ViewHolderItemHeader) holder).ivHead, options);
                    }
                    break;
                }
            }

        } else if (holder instanceof ViewHolderItemQuestion) {
            ViewHolderItemQuestion holderQuestion = (ViewHolderItemQuestion) holder;

            for (int i = 0; i < questionsPosList.size(); i++) {
                if (questionsPosList.get(i) == position) {
                    final Item item = questionsList.get(i);
                    String question = item.getTitle();
                    holderQuestion.tvQuestion.setText(question);

                    holderQuestion.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MainActivity mainActivity = (MainActivity) context;
                            FrgDetail frgDetail = new FrgDetail();
                            Bundle arguments = new Bundle();
                            arguments.putSerializable("item", item);
                            frgDetail.setArguments(arguments);
                            mainActivity.addFragment(frgDetail);
                        }
                    });
                    break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_QUESTION;
    }

    private boolean isPositionHeader(int position) {
        for (int pos : headersPosList) {
            if (pos == position) {
                return true;
            }
        }
        return false;
    }

    static class MainViewHolder extends RecyclerView.ViewHolder {
        public MainViewHolder(View view) {
            super(view);
        }
    }

    static class ViewHolderItemHeader extends MainViewHolder {

        private final TextView tvHead;
        private final ImageView ivHead;

        public ViewHolderItemHeader(View view) {
            super(view);
            tvHead = (TextView) view.findViewById(R.id.tvItemHeader);
            ivHead = (ImageView) view.findViewById(R.id.ivItemHeader);
        }
    }

    static class ViewHolderItemQuestion extends MainViewHolder {
        private final View mView;
        private final TextView tvQuestion;

        public ViewHolderItemQuestion(View view) {
            super(view);
            mView = view;
            tvQuestion = (TextView) view.findViewById(R.id.tvItemQuestion);
        }
    }

}
