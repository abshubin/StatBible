package com.example.andrew.statbible.activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.R.layout;

import com.example.andrew.statbible.R;
import com.example.andrew.statbible.tools.BibleDAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {


    Button mGoButton;
    Spinner mBookSelector;
    Spinner mStartChapter;
    Spinner mEndChapter;
    BibleDAO book;

    int start = 0;
    int end = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoButton = (Button) findViewById(R.id.goButton);
        mGoButton.setEnabled(false);

        mBookSelector = (Spinner) findViewById(R.id.bookSpinner);

        mStartChapter = (Spinner) findViewById(R.id.startChapterSpinner);
        mStartChapter.setEnabled(false);

        mEndChapter = (Spinner) findViewById(R.id.endChapterSpinner);
        mEndChapter.setEnabled(false);

        mBookSelector.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, BibleDAO.getBookNames()));
        mBookSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                book = new BibleDAO(getBookFile((String) adapterView.getItemAtPosition(i)));
                Integer[] chapterNumbers = getRange(1, book.getChapterCount());
                mStartChapter.setEnabled(true);
                mStartChapter.setAdapter(new ArrayAdapter<Integer>(MainActivity.this,
                        layout.simple_spinner_item, chapterNumbers));
                mStartChapter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        start = (Integer) adapterView.getItemAtPosition(i);
                        Integer[] chapterNumbers = getRange(start, book.getChapterCount());
                        mEndChapter.setEnabled(true);
                        mEndChapter.setAdapter(new ArrayAdapter<Integer>(MainActivity.this,
                                layout.simple_spinner_item, chapterNumbers));
                        mEndChapter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                mGoButton.setEnabled(true);
                                end = (Integer) adapterView.getItemAtPosition(i);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                mGoButton.setEnabled(false);
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        mEndChapter.setEnabled(false);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mEndChapter.setEnabled(false);
                mStartChapter.setEnabled(false);
            }
        });

        mGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (start > 0 && end > 0 && book != null) {
                    String[] range = book.getRange(start, 1, end, book.getVerseCount(end));
                    Intent intent = PassageActivity.newIntent(MainActivity.this, range[0], range[1]);
                    startActivity(intent);
                }
            }
        });
    }

    private File getBookFile(String bookName) {
        // This bit retrieved from https://stackoverflow.com/questions/34709210/creating-a-file-object-from-a-resource
        String filename = "";
        int booknum = BibleDAO.getBookNumber(bookName);
        if (booknum > 9) filename += "0";
        filename += booknum + " - " + bookName;
        InputStream inStream;
        try {
            inStream = getAssets().open(filename);
            File tempFile = File.createTempFile("foo", "bar");
            byte[] buffer = new byte[1024];
            int read;
            OutputStream out = new FileOutputStream(tempFile);
            while ((read = inStream.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Integer[] getRange(int first, int last) {
        Integer[] range = new Integer[last - first + 1];
        for (int i = 0; i < range.length; i++) {
            range[i] = i + first;
        }
        return range;
    }
}
