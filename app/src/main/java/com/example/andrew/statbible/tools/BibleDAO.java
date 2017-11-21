package com.example.andrew.statbible.tools;

/**
 * Created by andrew on 11/20/17.
 */

public class BibleDAO {

    private String bookName;
    private int startChapter;
    private int startVerse;
    private int endChapter;
    private int endVerse;
    private String[] text; // divided by verses

    // Gets just the book specified
    public BibleDAO(String book) {
        boolean
    }

    public String getVerse(int chapter, int verse) {
        return null;
    }

    public String getRange(int startChapter, int startVerse,
                           int endChapter,   int endVerse) {
        return null;
    }

    private static String getBookHeader(String simpleName) {
        final String[] names = getBookNames();

        switch (simpleName) {
            case names[0]:
                return "The First Book of Moses:  Called Genesis";
            case "exodus":
                return "The Second Book of Moses:  Called Exodus";
            case "leviticus":
                return "The Third Book of Moses:  Called Leviticus";
            case "numbers":
                return "The Fourth Book of Moses:  Called Numbers";
            case ""
        }
    }

    private static String[] getBookNames() {
        return { "Genesis", "Exodus", "Numbers", "Deuteronomy", "Joshua", "Judges",
                 "Ruth", "1 Samuel", "2 Samuel", "1 Kings", "2 Kings", "1 Chronicles",
                 "2 Chronicles", "Ezra", "Nehemiah", "Esther", "Job", "Psalms", "Proverbs",
                 "Ecclesiastes",
    }
}
