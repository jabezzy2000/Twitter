package com.codepath.apps.restclienttemplate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeContainer;
    TwitterClient client;
    RecyclerView rvTweets;
    List<Tweet> tweets;
    TweetsAdapter adapter;
    Button button;
    public static final String TAG = "TimeLineActivity";
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline); //sets view content to activity_timeline

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        client = TwitterApp.getRestClient(TimelineActivity.this);
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

        //setting a listener to whenever a refresh is needed after a swipe action
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tweets.clear(); // Calling clear function from adapter to clear current array
                populateHomeTimeLine(); //Calling function populateHomeTimeLine to add to now empty array and notify adapter data change
                swipeContainer.setRefreshing(false); //ends refresh after array is refreshed and timeline is populated
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu...this add items to the action bar if present
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.compose) {
            //this means the compose icon is tapped
            //navigate to compose activity
            Toast.makeText(this,"toast",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ComposeActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            //get data from Intent
            Tweet tweet = Parcels.unwrap(data.getParcelableExtra("tweet"));
            //update activity with data gotten from intent
            tweets.add(0,tweet); //adding tweet to the very first position in the array
            adapter.notifyItemInserted(0);
            rvTweets.smoothScrollToPosition(0);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
//      private void populateHomeTimeLine() {
//        client.getHomeTimeline(new Utilities.JSONHandlerVariable);
//      } --- ask Omar why this doesnt work


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