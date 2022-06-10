package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{
    Context context;
    List<Tweet> tweets;
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet= tweets.get(position);
        holder.bind(tweet);
    }
    @Override
    public int getItemCount() {
        return tweets.size();
    } //returns item count

    //creating a method to clear current data (tweets) from array
    public void cleardata() {
        tweets.clear();
        notifyDataSetChanged();
    }

    //creating a method to load new data (tweets) into array
    public void refreshdata() {
        tweets.addAll(tweets);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        ImageView TweetImage;
        TextView tvUsername; // did this
        TextView tvtime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage= itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            TweetImage = itemView.findViewById(R.id.TweetImage);
            tvUsername = itemView.findViewById((R.id.tvName));
            tvtime = itemView.findViewById(R.id.tvtime);
        }



        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            tvtime.setText(Utilities.getsimpletime(tweet.createdAt));
            tvUsername.setText(tweet.user.username);
           Utilities.roundedImage(context,tweet.user.profileImageUrl,ivProfileImage,60); //Using method defined in Utilities
            if(tweet.imageurl!= null) {
                TweetImage.setVisibility(View.VISIBLE);
                Utilities.setImage(context, tweet.imageurl, TweetImage);
            }
            else {
                TweetImage.setVisibility(View.GONE);
            }
        }
    }
}
