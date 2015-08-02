package com.vanbios.helptestapp.database;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.vanbios.helptestapp.AppController;
import com.vanbios.helptestapp.fragments.FrgTopicsList;
import com.vanbios.helptestapp.objects.Item;
import com.vanbios.helptestapp.utils.SharedPref;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public class DataSrc {

    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    public DataSrc(Context context) {
        dbHelper = new DbHelper(context);
        this.context = context;
    }

    public void openLocalToWrite() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void openLocalToRead() throws SQLException {
        db = dbHelper.getReadableDatabase();
    }

    public void closeLocal() {
        db.close();
    }


    public void insertData(LinkedHashMap<String, ArrayList<Item>> data) {

        ContentValues cv1 = new ContentValues();
        ContentValues cv2 = new ContentValues();
        openLocalToWrite();

        Iterator iterator = data.entrySet().iterator();

        int ansId = 1;

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            ArrayList<Item> itemList = new ArrayList<>();
            itemList.addAll((ArrayList<Item>) pair.getValue());

            if (itemList.size() > 0) {

                for (int i = 0; i < itemList.size(); i++) {

                    Item item = itemList.get(i);

                    int id = item.getId();
                    String title = item.getTitle();
                    String category = item.getCategory();
                    ArrayList<String> contentList = item.getContentList();

                    cv1.put("title", title);
                    cv1.put("category", category);

                    int count = db.update("Questions", cv1, "id_question = '" + id + "' ", null);
                    if (count == 0) {
                        cv1.put("id_question", id);
                        db.insert("Questions", null, cv1);
                    }

                    for (int j = 0; j < contentList.size(); j++) {

                        String content = contentList.get(j);

                        cv2.put("id_question", id);
                        cv2.put("content", content);

                        int count1 = db.update("Answers", cv2, "id_answer = '" + ansId + "' ", null);
                        if (count1 == 0) {
                            db.insert("Answers", null, cv2);
                        }

                        ansId++;
                    }
                }
            }
        }
        SharedPref sharedPref = new SharedPref(context);
        sharedPref.setDataPresentStatus(true);
        closeLocal();

        Intent insertData = new Intent(FrgTopicsList.BROADCAST_FRG_TOPICS_ACTION);
        insertData.putExtra(FrgTopicsList.PARAM_STATUS_FRG_TOPICS, FrgTopicsList.STATUS_UPDATE_FRG_TOPICS);
        AppController.getContext().sendBroadcast(insertData);
    }

    public LinkedHashMap<String, ArrayList<Item>> getData() {

        LinkedHashMap<String, ArrayList<Item>> result = new LinkedHashMap<>();

        String selectQuery = "SELECT DISTINCT category FROM Questions " +
                "ORDER BY id_question asc ";
        openLocalToRead();
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<String> categoriesList = new ArrayList<>();

        if (cursor.moveToFirst()) {

            do {
                String cat = cursor.getString(0);
                categoriesList.add(cat);
            }
            while (cursor.moveToNext());
        }

        for (String category : categoriesList) {

            ArrayList<Item> itemList = new ArrayList<>();

            selectQuery = "SELECT id_question, title FROM Questions " +
                    "WHERE category = '" + category + "' ";

            cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                int idColIndex = cursor.getColumnIndex("id_question");
                int titleColIndex = cursor.getColumnIndex("title");

                do {
                    int id = cursor.getInt(idColIndex);
                    String title = cursor.getString(titleColIndex);

                    ArrayList<String> contentList = new ArrayList<>();
                    selectQuery = "SELECT content FROM Answers " +
                            "WHERE id_question = '" + id + "' " +
                            "ORDER BY id_question asc, id_answer asc ";
                    Cursor cursor1 = db.rawQuery(selectQuery, null);
                    if (cursor1.moveToFirst()) {
                        do {
                            String content = cursor1.getString(0);
                            contentList.add(content);
                        }
                        while (cursor1.moveToNext());

                        cursor1.close();
                    }
                    Item item = new Item(id, title, category, contentList);
                    itemList.add(item);
                }
                while (cursor.moveToNext());
            }

            result.put(category, itemList);
        }

        cursor.close();
        closeLocal();

        return result;
    }

}
