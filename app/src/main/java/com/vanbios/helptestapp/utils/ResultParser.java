package com.vanbios.helptestapp.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.vanbios.helptestapp.AppController;
import com.vanbios.helptestapp.objects.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class ResultParser {


    public static void sendGetHelpReq(Context context) {

        String tag_string_req = "string_req";

        final String URL = "http://mocksvc.mulesoft.com/mocks/2b93657d-bbe5-4d4f-bd2f-cea2128712fb/mocks/8892f254-1de8-49bb-be7c-abe8cb154ab9/mocks/36c7af27-c2be-4ec5-976f-bb91d0eef09a/api/v1/help";

        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONArray data = new JSONObject(response).getJSONArray("data");

                    LinkedHashMap<String, ArrayList<Item>> result = new LinkedHashMap<>();

                    for (int i = 0; i < data.length(); i++) {

                        JSONObject head = data.getJSONObject(i);
                        String title = head.getString("title");

                        ArrayList<Item> itemList = new ArrayList<>();

                        JSONArray items = head.getJSONArray("items");

                        for (int j = 0; j < items.length(); j++) {

                            JSONObject item = items.getJSONObject(j);

                            int id = item.getInt("id");
                            String itemTitle = item.getString("title");
                            String category = item.getString("category");

                            ArrayList<String> contentList = new ArrayList<>();

                            JSONArray content = item.getJSONArray("content");

                            for (int k = 0; k < content.length(); k++) {

                                JSONObject contItem = content.getJSONObject(k);

                                String type = contItem.getString("type");

                                if (type.equals("text")) {
                                    String textContent = contItem.getString("content");
                                    contentList.add(textContent);
                                }
                            }
                            Item itemObj = new Item(id, itemTitle, category, contentList);
                            itemList.add(itemObj);
                        }
                        result.put(title, itemList);
                    }

                    InfoFromDB.getInstance().getDataSource().insertData(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pDialog.hide();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("COLLIDER", "Error: " + error.getMessage());
                pDialog.hide();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("Authorization", "ACCESS_TOKEN");
                return hashMap;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
