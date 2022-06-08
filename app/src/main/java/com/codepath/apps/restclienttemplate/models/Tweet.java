package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
@Parcel
public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public String imageurl;
    public Tweet(){}; //empty constructor required by parceler

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        //If the tweet is long, Twitter automatically truncates the tweet so we want to get the full thing instead of the shortened version
        //hence creating an if/else block to handle that
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        if(jsonObject.has("full_text")) {
            tweet.body = jsonObject.getString(("full_text"));
        }
        else {
            tweet.body = jsonObject.getString("text");
        }
        //if the tweet has extras, It'll probably be in entities object
        //hence we want to get the image from the entities Object
        if(jsonObject.getJSONObject("entities").has("media")){
            tweet.imageurl = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url_https");
        }
        else {
            jsonObject.getJSONObject("entities");
        }
        return tweet;

    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException{
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}


