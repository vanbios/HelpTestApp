package com.vanbios.helptestapp.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.vanbios.helptestapp.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;




public class ResultParser {


    public static void sendPostLoginReq(final Context context) {

        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";


        JSONObject js = new JSONObject();
        try {

            js.put("email", "example@example.com");
            js.put("password", "password");

        }catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://mocksvc.mulesoft.com/mocks/2b93657d-bbe5-4d4f-bd2f-cea2128712fb/mocks/8892f254-1de8-49bb-be7c-abe8cb154ab9/mocks/36c7af27-c2be-4ec5-976f-bb91d0eef09a/api/v1/account/login";

        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, js,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("COLLIDER", response.toString());
                        pDialog.hide();

                        sendGetHelpReq(context);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("COLLIDER", "Error: " + error.getMessage());
                pDialog.hide();
            }
        }) {

        };

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }



    public static void sendGetHelpReq(Context context) {

        // Tag used to cancel the request
        String  tag_string_req = "string_req";

        String url = "http://mocksvc.mulesoft.com/mocks/2b93657d-bbe5-4d4f-bd2f-cea2128712fb/mocks/8892f254-1de8-49bb-be7c-abe8cb154ab9/mocks/36c7af27-c2be-4ec5-976f-bb91d0eef09a/api/v1/help";

        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("COLLIDER", response);
                pDialog.hide();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("COLLIDER", "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }



}
