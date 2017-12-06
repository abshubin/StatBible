package com.example.andrew.statbible.activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.R.layout;
import android.R.drawable;

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
        mGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String message = book.getBookName() + " " + start + " - " + end;
//                Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
//                toast.show();

                if (start > 0 && end > 0 && book != null) {
                    String[] range = book.getRange(start, 1, end, book.getVerseCount(end));
                    Intent intent = PassageActivity.newIntent(MainActivity.this, range[0], range[1]);
                    mGoButton.setBackgroundResource(drawable.ic_menu_search);
                    startActivity(intent);
                }
            }
        });

        mEndChapter = (Spinner) findViewById(R.id.endChapterSpinner);
        mEndChapter.setEnabled(false);
        mEndChapter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                end = Integer.parseInt((String) adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });

        mStartChapter = (Spinner) findViewById(R.id.startChapterSpinner);
        mStartChapter.setEnabled(false);
        mStartChapter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                start = Integer.parseInt((String) adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });

        mBookSelector = (Spinner) findViewById(R.id.bookSpinner);
        this.setSpinnerContent(mBookSelector, BibleDAO.getBookNames());
        mBookSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                book = new BibleDAO((String) adapterView.getItemAtPosition(i), getApplicationContext());
                int count = book.getChapterCount();
                String[] chapters = getRange(1, count);
                setSpinnerContent(mStartChapter, chapters);
                mStartChapter.setEnabled(true);
                setSpinnerContent(mEndChapter, chapters);
                mEndChapter.setEnabled(true);
                mGoButton.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });
    }

    private void setSpinnerContent(Spinner spinner, String[] content) {
        spinner.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                layout.simple_spinner_item, content));
    }

    private static String[] getRange(int first, int last) {
        String[] range = new String[last - first + 1];
        for (int i = 0; i < range.length; i++) {
            range[i] = (i + first) + "";
        }
        return range;
    }
}
