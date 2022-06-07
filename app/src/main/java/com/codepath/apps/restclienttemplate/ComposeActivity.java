package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ComposeActivity extends AppCompatActivity {

    EditText multiLine;
    Button button;
    publc static final int Max_Tweet_Length = 140;
    public static final String TAG = "ComposeActivity"; //why isnt this necessary?



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        multiLine = findViewById(R.id.tvMultiLine);
        button = findViewById(R.id.composebutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetcontent = multiLine.getText().toString();
                if(tweetcontent.isEmpty()) {
                    Toast.makeText(ComposeActivity.this,"Empty tweet", Toast.LENGTH_SHORT).show();
                    return; //why is this returning early?
                }
                if(tweetcontent.length() > Max_Tweet_Length) {
                    Toast.makeText(ComposeActivity.this,"Error: Tweet exceeds 140 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ComposeActivity.this,tweetcontent, Toast.LENGTH_SHORT).show();
            }

            });
    }
}