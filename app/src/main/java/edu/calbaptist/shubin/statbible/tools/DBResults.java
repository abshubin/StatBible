package edu.calbaptist.shubin.statbible.tools;

import android.database.Cursor;

/**
 * Created by andrew on 11/29/17.
 */

public class DBResults implements DatabaseResults {

    private Cursor results;

    public DBResults(Cursor cursor) {
        results = cursor;
    }

    @Override
    public boolean moveToNext() {
        return results.moveToNext();
    }

    @Override
    public int getRowNum() {
        return results.getPosition();
    }

    @Override
    public int getPartCount() {
        return results.getColumnCount();
    }

    @Override
    public String getString(String partName) {
        int index = results.getColumnIndex(partName);
        return results.getString(index);
    }

    @Override
    public int getInt(String partName) {
        int index = results.getColumnIndex(partName);
        return results.getInt(index);
    }
}
