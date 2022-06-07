package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {
    TwitterClient client;
    RecyclerView rvTweets;
    List<Tweet> tweets;
    TweetsAdapter adapter;
    Button button;
    public static final String TAG = "TimeLineActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

         client = TwitterApp.getRestClient(this);
         rvTweets = findViewById(R.id.rvTweets); //finding recycler view by ID
        button = findViewById(R.id.logout);
        tweets = new ArrayList<>();
        adapter = new TweetsAdapter(this, tweets);
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        rvTweets.setAdapter(adapter);
        populateHomeTimeLine();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogoutButton();
            }
        });
    }

    private void populateHomeTimeLine() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG, "onSuccess: Success " + json.toString());
                JSONArray jsonArray = json.jsonArray;
                try {
                    tweets.addAll(Tweet.fromJsonArray(jsonArray));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e(TAG, "Json exception",e );
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "onFailure: here" + response, throwable );
//                loge + response as in above shows the reason why youre having an error..good for debugging

            }
        });
    }

    public void onLogoutButton() {
        TwitterApp.getRestClient(TimelineActivity.this).clearAccessToken();
        //code above was meant to reset the api ( or log out)
        // navigate back to the login screen
        Intent i = new Intent(TimelineActivity.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //this makes sure the back button is made inactive
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //same as above
        startActivity(i);
    }
}