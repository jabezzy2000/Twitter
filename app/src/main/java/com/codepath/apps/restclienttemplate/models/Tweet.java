package com.codepath.apps.restclienttemplate.models;

import org.json.JSONObject;

public class Tweet {
    Public String body;
    Public String createdAt;
    public User user;

    public static Tweet fromJson(JSONObject jsonObject){
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        return tweet

    }

}
