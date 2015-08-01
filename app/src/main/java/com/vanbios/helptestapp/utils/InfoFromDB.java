package com.vanbios.helptestapp.utils;

import com.vanbios.helptestapp.AppController;
import com.vanbios.helptestapp.database.DataSource;


public class InfoFromDB {

    private final DataSource dataSource = new DataSource(AppController.getContext());

    private static volatile InfoFromDB instance;


    public DataSource getDataSource() {
        return dataSource;
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
