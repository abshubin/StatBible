package com.example.andrew.statbible.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.andrew.statbible.R;

public class PassageActivity extends AppCompatActivity {

    private static final String EXTRA_PASSAGE_REFERENCE =
            "com.example.andrew.statbible.passage_reference";
    private static final String EXTRA_PASSAGE_TEXT =
            "com.example.andrew.statbible.passage_text";

    Button mInfoButton;

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
    }
}
