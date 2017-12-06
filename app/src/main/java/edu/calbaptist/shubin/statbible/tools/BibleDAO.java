package edu.calbaptist.shubin.statbible.tools;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by andrew on 11/20/17.
 */

public class BibleDAO {

    public static final String NEW_TESTAMENT = "NEW";
    public static final String OLD_TESTAMENT = "OLD";
    public static final String INVALID_REFERENCE = "INVALID REFERENCE";

    private String bookName;
    private DatabaseInterface db;
    private int[] verseCounts; // verse counts for each chapter, 1-indexed (0 index unused)

    // Gets just the book specified -- for testing only
    public BibleDAO(String book, Context context) {
        if (context != null) {  // Use DBHelper
            db = new DBHelper(context);
        } else {  // Testing, use DBTestHelper
            db = new DBTestHelper();
        }
        try {
            db.createDataBase();
        } catch (Exception e) {
            throw new Error("Unable to create database.");
        }

        try {
            db.openDataBase();
            bookName = filter(book);
            countVerses();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getBookName() {
        return bookName;
    }

    /*
     * Returns:
     *
     * result[0]... String denoting the reference of the returned text.
     *
     * result[1]... The returned text.
     */
    public String[] getVerse(int chapter, int verse) {
        String reference = bookName + " " + chapter + ":" + verse;
        String text = "";
        DatabaseResults results = db.select("text from verses where " +
                                            "book = " + getBookNumber(bookName) + " and " +
                                            "chapter = " + chapter + " and " +
                                            "verse = " + verse);
        if (results.moveToNext()) {
            text = results.getString("text");
        } else {
            text = INVALID_REFERENCE;
        }
        String[] result = {reference, text.trim()};
        return result;
    }

    /*
     * Returns:
     *
     * result[0]... String denoting the reference of the returned text.
     *
     * result[1]... The returned text.
     */
    public String[] getRange(int startChapter, int startVerse,
                             int endChapter,   int endVerse) {

        String reference = bookName + " ";
        String text = "";
        boolean misorderedNeighborVerses = false;
        if (startChapter == endChapter) {
            reference += startChapter + ":" + startVerse + "-" + endVerse;
            misorderedNeighborVerses = startVerse > endVerse;
        } else {
            reference += startChapter + ":" + startVerse + " - "
                    + endChapter + ":" + endVerse;
        }
        if (startChapter == endChapter && startVerse == endVerse) {
            return this.getVerse(startChapter, startVerse);
        }
        boolean failCondition1 = startChapter < 1 || startVerse < 1
                                    || endChapter < 1 || endVerse < 1;
        boolean failCondition2 = startChapter > getChapterCount()
                                    || endChapter > getChapterCount();
        if (failCondition1 || failCondition2 || misorderedNeighborVerses) {
            text = INVALID_REFERENCE;
        } else if (startVerse > verseCounts[startChapter] || endVerse > verseCounts[endChapter]) {
            text = INVALID_REFERENCE;
        } else {
            DatabaseResults results = db.select("chapter, verse, text from verses where " +
                    "book = " + getBookNumber(bookName) + " and " +
                    "chapter >= " + startChapter + " and " +
                    "chapter <= " + endChapter);
            while (results.moveToNext()) {
                int verse = results.getInt("verse");
                int chapter = results.getInt("chapter");
                boolean condition1 = (chapter == startChapter) && (verse >= startVerse);
                boolean condition2 = (chapter > startChapter) && (chapter < endChapter);
                boolean condition3 = (chapter == endChapter) && (verse <= endVerse);

                if (condition1 || condition2 || condition3) {
                    text += results.getString("text").trim() + " ";
                }

                if (chapter == endChapter && verse > endVerse) {
                    break;
                }
            }
        }
        if (text.equals("")) {
            text = INVALID_REFERENCE;
        }
        String[] result = {reference, text.trim()};
        return result;
    }

    /*
     * Returns the number of verses in the specified chapter.
     */
    public int getVerseCount(int chapter) {
        if (chapter >= verseCounts.length || chapter < 1) {
            return 0;
        }
        return verseCounts[chapter];
    }

    public int getChapterCount() {
        if (verseCounts == null) return 0;
        return verseCounts.length - 1;
    }

    private void countVerses() {
        DatabaseResults results = db.select("chapter, verse from verses "
                                            + "where book = " + BibleDAO.getBookNumber(bookName));
        ArrayList<Integer> chapters = new ArrayList<>();
        int currentChapter = 1;
        int verseCount = 0;
        chapters.add(verseCount);  // To create 1-indexing.
        while (results.moveToNext()) {
            int chapter = results.getInt("chapter");
            int verse = results.getInt("verse");
            if (chapter != currentChapter) {
                currentChapter++;
                chapters.add(verseCount);
                verseCount = 1;
            } else {
                verseCount++;
            }
        }
        chapters.add(verseCount);

        verseCounts = new int[chapters.size()];
        for (int i = 0; i < verseCounts.length; i++) {
            verseCounts[i] = chapters.get(i);
        }
    }

    public static int getBookNumber(String simpleName) {
        final String[] names = getBookNames();

        for (int i = 0; i < 66; i++) {
            if (simpleName.equals(names[i])) {
                return i + 1;
            }
        }

        return 0;
    }

    public static String[] getBookNames() {
        String[] books = { "Genesis", "Exodus", "Leviticus", "Numbers", "Deuteronomy", "Joshua", "Judges",
                 "Ruth", "1 Samuel", "2 Samuel", "1 Kings", "2 Kings", "1 Chronicles",
                 "2 Chronicles", "Ezra", "Nehemiah", "Esther", "Job", "Psalms", "Proverbs",
                 "Ecclesiastes", "Song of Solomon", "Isaiah", "Jeremiah", "Lamentations",
                 "Ezekiel", "Daniel", "Hosea", "Joel", "Amos", "Obadiah", "Jonah", "Micah",
                 "Nahum", "Habakkuk", "Zephaniah", "Haggai", "Zechariah", "Malachi",
                 "Matthew", "Mark", "Luke", "John", "Acts", "Romans", "1 Corinthians",
                 "2 Corinthians", "Galatians", "Ephesians", "Philippians", "Colossians",
                 "1 Thessalonians", "2 Thessalonians", "1 Timothy", "2 Timothy", "Titus",
                 "Philemon", "Hebrews", "James", "1 Peter", "2 Peter", "1 John", "2 John",
                 "3 John", "June", "Revelation" };
        return books;
    }

    private static String[] getBookNames(String testament) {
        String[] books = getBookNames();
        String[] set;

        switch (testament) {
            case OLD_TESTAMENT:
                set = Arrays.copyOfRange(books, 0, 39);
                break;
            case NEW_TESTAMENT:
                set = Arrays.copyOfRange(books, 39, 66);
                break;
            default:
                set = books;
                break;
        }

        return set;
    }

    private static String filter(String bookName) {
        String[] validNames = getBookNames();
        for (String validName : validNames) {
            if (validName.equals(bookName)) {
                return validName;
            }
        }
        return "Mark";  // Default. Why? No particular reason...
    }
}
