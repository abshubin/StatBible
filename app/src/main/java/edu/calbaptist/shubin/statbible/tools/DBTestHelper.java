package edu.calbaptist.shubin.statbible.tools;

import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by andrew on 11/29/17.
 */

public class DBTestHelper implements DatabaseInterface {

    private static final String DB_FULL_PATH = "/home/andrew/AndroidStudioProjects/StatBible/"
                                            + "app/src/test/java/com/example/andrew/edu.calbaptist.shubin.statbible/kjv.db";

    private Connection conn;

    @Override
    public DatabaseResults select(String query) {
        DatabaseResults results = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("select " + query);
            results = new DBTestResults(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public void createDataBase() throws IOException {
        // Nothing needs to be done here in testing.
    }

    @Override
    public void openDataBase() throws SQLException {
        conn = null;
        try {
            String url = "jdbc:sqlite:" + DB_FULL_PATH;
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new Error("Could not close database.");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Not used for testing
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not used for testing
    }
}
