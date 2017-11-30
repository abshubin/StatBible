package com.example.andrew.statbible.tools;

import com.example.andrew.statbible.tools.DatabaseResults;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by andrew on 11/29/17.
 */

public class DBTestResults implements DatabaseResults {

    private ResultSet results;

    public DBTestResults(ResultSet resultSet) {
        results = resultSet;
    }

    @Override
    public boolean moveToNext() {
        try {
            return results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getRowNum() {
        try {
            return results.getRow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int getPartCount() {
        try {
            ResultSetMetaData metaData = results.getMetaData();
            return metaData.getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String getString(String partName) {
        try {
            return results.getString(partName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getInt(String partName) {
        try {
            return results.getInt(partName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
