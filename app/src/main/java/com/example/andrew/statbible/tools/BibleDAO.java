package com.example.andrew.statbible.tools;

import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
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
    private File book;
    private int[] verseCounts; // verse counts for each chapter, 1-indexed (0 index unused)

    // Gets just the book specified -- for testing only
    public BibleDAO(String book) {

        /* OLD WAY OF DOING IT -- DOESN'T WORK IN APP */
        File dir = new File("/home/andrew/AndroidStudioProjects/StatBible/app/" +
                            "src/main/java/com/example/andrew/statbible/tools/kjv");
        int bookNum = getBookNumber(book);
        this.bookName = book;
        if (bookNum == 0) {
            bookNum = getBookNumber("Mark");  // No particular reason...
            this.bookName = "Mark";
        }
        for (File file : dir.listFiles()) {
            if (file.getName().startsWith(bookNum + "")) {
                this.book = file;
                countVerses();
                return;
            }
        }
    }

    // Also sets up the book, but takes the file object instead...
    public BibleDAO(File book) {
        String[] parts = book.getName().split(" ");
        bookName = parts[parts.length - 1];
        countVerses();
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
        String referenceText = this.bookName + " " + chapter + ":" + verse;
        String passageText = "";

        try (BufferedReader br = new BufferedReader(new FileReader(book))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(chapter + ":" + verse + " ")) {
                    passageText = stripReference(line);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        if (passageText.equals("")) {
            passageText = INVALID_REFERENCE;
        }

        String[] resultPackage = {referenceText, passageText};
        return resultPackage;
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
        String referenceText = this.bookName + " ";
        if (startChapter == endChapter) {
            referenceText += startChapter + ":" + startVerse + "-" + endVerse;
        } else {
            referenceText += startChapter + ":" + startVerse;
            referenceText += " - ";
            referenceText += endChapter + ":" + endVerse;
        }
        String passageText = "";
        if ((endChapter == startChapter && endVerse < startVerse)
                || (endChapter < startChapter)) {
            passageText = INVALID_REFERENCE;
        }
        String[] start = getVerse(startChapter, startVerse);
        String[] end   = getVerse(endChapter,   endVerse);
        if (start[1].equals(INVALID_REFERENCE) || end[1].equals(INVALID_REFERENCE)) {
            passageText = INVALID_REFERENCE;
        }

        if (passageText.equals("")) {
            try (BufferedReader br = new BufferedReader(new FileReader(book))) {
                String line;
                boolean started = false;
                while ((line = br.readLine()) != null) {
                    if (started) {
                        if (line.startsWith(endChapter + ":" + endVerse + " " + end[1])) {
                            passageText += " " + stripReference(line);
                            break;
                        } else {
                            passageText += " " + stripReference(line);
                        }
                    } else {
                        if (line.startsWith(startChapter + ":" + startVerse + " " + start[1])) {
                            passageText = stripReference(line);
                            started = true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String[] resultPackage = {referenceText, passageText};
        return resultPackage;
    }

    /*
     * Returns the number of verses in the specified chapter.
     */
    public int getVerseCount(int chapter) {
        if (chapter >= verseCounts.length || chapter < verseCounts.length) {
            return 0;
        }
        return verseCounts[chapter];
    }

    public int getChapterCount() {
        return verseCounts.length - 1;
    }

    private void countVerses() {
        try(BufferedReader br = new BufferedReader(new FileReader(book))) {
            ArrayList<Integer> chapters = new ArrayList<>(); // tallys up the chapters and their
                                                             // verse counts.
            String line;
            int currentChapter = 0;
            int currentVerseCount = 0;
            while ((line = br.readLine()) != null) {
                if (line.substring(0, 5).contains(":")) {
                    String reference = line.split(" ")[0];
                    int chapter = Integer.parseInt(reference.split(":")[0]);
                    if (chapter == currentChapter) {
                        currentVerseCount++;
                    } else {
                        currentChapter++;
                        chapters.add(currentVerseCount);
                        currentVerseCount = 1;
                    }
                }
            }
            chapters.add(currentVerseCount);

            verseCounts = new int[chapters.size()];
            for (int i = 0; i < chapters.size(); i++) {
                verseCounts[i] = chapters.get(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String stripReference(String rawVerse) {
        if (!rawVerse.split(" ")[0].contains(":")) {
            return rawVerse;
        }
        String cleanText = "";
        String[] passage = Arrays.copyOfRange(rawVerse.split(" "), 1, rawVerse.split(" ").length);
        for (String word : passage) {
            if (word.equals(passage[0])) {
                cleanText = word;
            } else {
                cleanText += " " + word;
            }
        }
        return cleanText;
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
}
