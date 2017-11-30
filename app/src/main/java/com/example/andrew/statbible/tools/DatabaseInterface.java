package com.example.andrew.statbible.tools;

import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by andrew on 11/29/17.
 */

public interface DatabaseInterface {

    public void createDataBase() throws IOException;

    public void openDataBase() throws SQLException;

    public void close();

    public void onCreate(SQLiteDatabase db);

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

    public DatabaseResults select(String query);
}
