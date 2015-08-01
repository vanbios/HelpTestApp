package com.vanbios.helptestapp.fragments;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.vanbios.helptestapp.AppController;
import com.vanbios.helptestapp.R;
import com.vanbios.helptestapp.objects.Item;

import java.util.ArrayList;

public class FrgDetail extends CommonFragment {

    private View view;
    private Item item;


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

        ImageView iv1 = (ImageView) view.findViewById(R.id.ivDetail1);
        ImageView iv2 = (ImageView) view.findViewById(R.id.ivDetail2);

        final String URL_IMAGE_1 = "http://504080.com//uploads/helpBlock/0d1ce04da41d7449cdcb62e7ec29de1d7fe4d84a.jpg";
        final String URL_IMAGE_2 = "http://504080.com//uploads/helpBlock/14f9963d5af8fbf2061cf0895c6149412c061404.jpg";

        loadImage(URL_IMAGE_1, iv1);
        loadImage(URL_IMAGE_2, iv2);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.layoutDetail);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 20, 0, 0);

        if (answers.size() > 1) {

            for (int i = 1; i < answers.size(); i++) {

                TextView textView = new TextView(getActivity());
                textView.setText(i+1 + ". " + answers.get(i));
                textView.setLayoutParams(layoutParams);
                linearLayout.addView(textView);
            }
        }
    }

    private void loadImage(String URL, final ImageView iv) {
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(URL, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("COLLIDER", "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    iv.setImageBitmap(response.getBitmap());
                }
            }
        });
    }


    @Override
    public String getTitle() {
        return item.getCategory();
    }
}
