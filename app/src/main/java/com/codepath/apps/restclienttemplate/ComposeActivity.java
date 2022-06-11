package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;


import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {
    //creating variables to be tied with xml items
    EditText multiLine;
    Button button;
    public static final int Max_Tweet_Length = 140;
    TwitterClient client;
    public static final String TAG = "ComposeActivity";
    JsonHttpResponseHandler responseHandler= new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Headers headers, JsonHttpResponseHandler.JSON
        json) {
            Log.i(TAG, "onSuccess: Successfully published");
            try {
                Tweet tweet = Tweet.fromJson(json.jsonObject);
                Intent intent = new Intent(); //creating intent to pass back data to Parent activity (Timeline Activity)
                intent.putExtra("tweet", Parcels.wrap(tweet));
                setResult(RESULT_OK, intent);
                finish(); // finish closes the activity and sends the data back to parent activity
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
            Log.e(TAG, "onFailure: failure to publish tweet" + response, throwable);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose); //setting content view to activity compose layout/xml

        //tying the variables created above with the actual xml items
        multiLine = findViewById(R.id.tvMultiLine);
        button = findViewById(R.id.composebutton);
        client = TwitterApp.getRestClient(this);

        button.setOnClickListener(new View.OnClickListener() { //implementing an onclick listener to have something done whenever the button is clicked
            //whenever the button ( compose button in this case) is clicked,
            @Override
            public void onClick(View v) {
                String tweetcontent = multiLine.getText().toString();
                if(tweetcontent.isEmpty()) {
                    Toast.makeText(ComposeActivity.this,"Empty tweet", Toast.LENGTH_SHORT).show();
                    return; //these return statements are put in so that if this block of code runs, it will break over here
                    //(return statements exit loops)
                }
                if(tweetcontent.length() > Max_Tweet_Length) {
                    Toast.makeText(ComposeActivity.this,"Error: Tweet exceeds 140 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ComposeActivity.this,tweetcontent, Toast.LENGTH_SHORT).show();
                //client.publishtweet(tweetcontent, responseHandler);

                if(getIntent().hasExtra("tweet_to_reply_to")) {
                    Tweet tweetToReplyTo = Parcels.unwrap(getIntent().getParcelableExtra("tweet_to_reply_to"));
                    String idOfTweetToReplyTo = tweetToReplyTo.id;
                    String screenName = tweetToReplyTo.user.screenName;
                    client.replyTweet(idOfTweetToReplyTo,screenName + " "+ tweetcontent, responseHandler);

                }
                else {
                    client.publishtweet(tweetcontent, responseHandler);
                }
            }
            });
    }
}