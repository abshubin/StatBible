package edu.calbaptist.shubin.statbible.tools;

/**
 * Created by andrew on 11/29/17.
 */

public interface DatabaseResults {

    public boolean moveToNext();

    public int getRowNum();

    public int getPartCount();

    public String getString(String partName);

    public int getInt(String partName);
}
