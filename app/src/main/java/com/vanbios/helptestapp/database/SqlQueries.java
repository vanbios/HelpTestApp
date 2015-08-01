package com.vanbios.helptestapp.database;


public class SqlQueries {

    public static final String create_questions_table = "CREATE TABLE Questions (" +
            "id_question      INTEGER PRIMARY KEY," +
            "title            TEXT NOT NULL," +
            "category         TEXT NOT NULL" +
            ");";

    public static final String create_answers_table = "CREATE TABLE Answers (" +
            "id_answer        INTEGER PRIMARY KEY AUTOINCREMENT," +
            "id_question      INTEGER NOT NULL," +
            "content          TEXT NOT NULL" +
            ");";

}
