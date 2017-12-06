package edu.calbaptist.shubin.statbible.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.calbaptist.shubin.statbible.R;
import edu.calbaptist.shubin.statbible.tools.BibleDAO;
import edu.calbaptist.shubin.statbible.tools.FrequencyTrie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PassageActivity extends AppCompatActivity {

    private static final String EXTRA_PASSAGE_REFERENCE =
            "edu.calbaptist.shubin.statbible.passage_reference";
    private static final String EXTRA_PASSAGE_TEXT =
            "edu.calbaptist.shubin.statbible.passage_text";

    private static final int SCORE_THRESHOLD = 20;

    private FrequencyTrie trie;

    Button mInfoButton;
    TextView mTextView;

    public static Intent newIntent(Context packageContext, String passageReference,
                                   String passageText) {
        Intent intent = new Intent(packageContext, PassageActivity.class);
        intent.putExtra(EXTRA_PASSAGE_REFERENCE, passageReference);
        intent.putExtra(EXTRA_PASSAGE_TEXT, passageText);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passage);
        mInfoButton = (Button) findViewById(R.id.info_button);
        mInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                AlertDialog alertDialog = new AlertDialog.Builder(PassageActivity.this).create();
                alertDialog.setTitle("Formatting Info");
                alertDialog.setMessage(getString(R.string.info_text));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        mTextView = (TextView) findViewById(R.id.textView);
        String text = getIntent().getStringExtra(EXTRA_PASSAGE_TEXT);
        String reference = getIntent().getStringExtra(EXTRA_PASSAGE_REFERENCE);
        buildTextView(reference, text);
//        mTextView.setMovementMethod(new ScrollingMovementMethod());

//        String appTitle = getString(R.string.app_name);
        getSupportActionBar().setTitle(reference);
    }

    private void buildTextView(String reference, String text) {
        String bookText = getBookText(reference);
        trie = new FrequencyTrie(bookText, getStopwords());
        mTextView.setText("");
        for (String word : text.split(" ")) {
            if (trie.score(word.trim().toLowerCase()) < SCORE_THRESHOLD) {
                mTextView.append(word);
            } else {
                mTextView.append(getSpannable(word));
            }
            mTextView.append(" ");
        }
        mTextView.append("\n\n");
        makeLinksFocusable(mTextView);
    }

    private SpannableString getSpannable(final String word) {
        SpannableString link = makeLinkSpan(word, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String book = (String) getIntent().getStringExtra(EXTRA_PASSAGE_REFERENCE);
                book = book.split(" ")[0];
                if (book.equals("Psalm")) book += "s";
                String message = "WORD/PREFIX FREQUENCY \n(in " + book + ")";
                AlertDialog alert = new AlertDialog.Builder(PassageActivity.this).create();
                alert.setTitle(message);
                message = "";
                int[] partsCounts = trie.countParts(word);
                for (int i = 0; i < partsCounts.length; i++) {
                    if (partsCounts[i] > 0) {
                        message += "\n\"" + word.substring(0, i + 1) + "...\"  (" + partsCounts[i]
                                + " times)";
                    }
                }
                alert.setMessage(message);
                alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alert.show();
            }
        });
        return link;
    }

    private SpannableString makeLinkSpan(CharSequence text, View.OnClickListener listener) {
        SpannableString link = new SpannableString(text);
        link.setSpan(new ClickableString(listener), 0, text.length(),
                SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        return link;
    }

    private static class ClickableString extends ClickableSpan {
        private View.OnClickListener mListener;
        public ClickableString(View.OnClickListener listener) {
            mListener = listener;
        }
        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }
    }

    private void makeLinksFocusable(TextView tv) {
//        MovementMethod m = tv.getMovementMethod();
//        if ((m == null) || !(m instanceof LinkMovementMethod)) {
//            if (tv.getLinksClickable()) {
//                tv.setMovementMethod(LinkMovementMethod.getInstance());
//            }
//        }

        if (mTextView != null) mTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private String getBookText(String reference) {
        BibleDAO book = new BibleDAO(reference.split(" ")[0], getApplicationContext());
        int lastChapter = book.getChapterCount();
        String[] text = book.getRange(1, book.getVerseCount(1),
                                    lastChapter, book.getVerseCount(lastChapter));
        return text[1];
    }

    private String[] getStopwords() {
        try {
            ArrayList<String> stopwords = new ArrayList<>();
            InputStream inputStream = getApplicationContext().getAssets().open("kjv_stopwords.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            if (inputStream != null) {
                while ((line = reader.readLine()) != null) {
                    stopwords.add(line.trim());
                }
            }
            String[] wordList = new String[stopwords.size()];
            for (int i = 0; i < wordList.length; i++) {
                wordList[i] = stopwords.get(i);
            }
            return wordList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] minimum = {"a", "an", "and", "the", "then", "but", "so", "since"};  // shouldn't be needed...
        return minimum;
    }
}
