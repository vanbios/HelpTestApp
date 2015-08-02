package com.vanbios.helptestapp.utils;

import com.vanbios.helptestapp.AppController;
import com.vanbios.helptestapp.database.DataSrc;


public class InfoFromDB {

    private final DataSrc dataSrc = new DataSrc(AppController.getContext());

    private static volatile InfoFromDB instance;


    public DataSrc getDataSrc() {
        return dataSrc;
    }

    public static InfoFromDB getInstance() {
        InfoFromDB localInstance = instance;
        if (localInstance == null) {
            synchronized (InfoFromDB.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new InfoFromDB();
                }
            }
        }
        return localInstance;
    }

}
