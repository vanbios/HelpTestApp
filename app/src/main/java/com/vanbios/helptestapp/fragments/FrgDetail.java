package com.vanbios.helptestapp.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.vanbios.helptestapp.R;
import com.vanbios.helptestapp.objects.Item;

import java.util.ArrayList;

public class FrgDetail extends CommonFragment {

    private View view;
    private Item item;

    final String URL_IMAGE_1 = "http://504080.com//uploads/helpBlock/0d1ce04da41d7449cdcb62e7ec29de1d7fe4d84a.jpg";
    final String URL_IMAGE_2 = "http://504080.com//uploads/helpBlock/14f9963d5af8fbf2061cf0895c6149412c061404.jpg";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frg_detail, container, false);
        item = (Item) getArguments().getSerializable("item");
        setViews();

        return view;
    }

    private void setViews() {
        TextView tvQuestion = (TextView) view.findViewById(R.id.tvDetailQuestion);
        tvQuestion.setText(item.getTitle());

        ArrayList<String> answers = item.getContentList();

        TextView tvAnswer1 = (TextView) view.findViewById(R.id.tvDetailAnswer1);
        tvAnswer1.setText("1. " + answers.get(0));

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.layoutDetail);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 20, 0, 0);

        if (answers.size() > 1) {
            for (int i = 1; i < answers.size(); i++) {
                TextView textView = new TextView(getActivity());
                textView.setText(i + 1 + ". " + answers.get(i));
                textView.setLayoutParams(layoutParams);
                linearLayout.addView(textView);
            }
        }

        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .build();

        ImageView iv1 = (ImageView) view.findViewById(R.id.ivDetail1);
        ImageView iv2 = (ImageView) view.findViewById(R.id.ivDetail2);

        imageLoader.displayImage(URL_IMAGE_1, iv1, options);
        imageLoader.displayImage(URL_IMAGE_2, iv2, options);

        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageDialog(URL_IMAGE_1);
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageDialog(URL_IMAGE_2);
            }
        });
    }

    private void openImageDialog(String url) {
        Bundle args = new Bundle();
        args.putString("url", url);

        DialogFragment numericDialog = new FrgImageDialog();
        numericDialog.setArguments(args);
        numericDialog.show(getActivity().getSupportFragmentManager(), "imageDialog");
    }

    @Override
    public String getTitle() {
        return item.getCategory();
    }

}
