package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
//import android.widget.ImageButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;
import org.w3c.dom.Text;

public class TweetDetailActivity extends AppCompatActivity {
    //creating variables
    Tweet tweet;
    TextView tvUsernamedetail;
    TextView tvNamedetail;
    TextView tvBodydetail;
    TextView tvTime;
    ImageView ivProfilepicture;
    ImageView ivImageExtra;
    ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        //referencing/linking the variables to the items on the xml
        tvUsernamedetail = findViewById(R.id.tvUsernamedetail);
        tvNamedetail = findViewById(R.id.tvNamedetail);
        tvBodydetail = findViewById(R.id.tvBodydetail);
        ivProfilepicture = findViewById(R.id.ivProfilepicture);
        ivImageExtra = findViewById(R.id.ivImageExtra);
        tvTime = findViewById(R.id.tvTime);
        button = findViewById(R.id.btnReply);


        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        tvUsernamedetail.setText(tweet.user.username);
        tvBodydetail.setText(tweet.body);
        tvNamedetail.setText(tweet.user.screenName);
        tvTime.setText(Utilities.getSimpleTime(tweet.createdAt));

        Utilities.roundedImage(this,tweet.user.profileImageUrl,ivProfilepicture,60);
        Utilities.setImage(this,tweet.imageurl,ivImageExtra);



        //created xml
        // edited adapter
        //parceled class and created intent
        //edited tweetdetails activity




    }
}