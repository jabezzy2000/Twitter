package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {
    public String name;
    public String screenName;
    public String profileImageUrl;
    public String username;
    public String time;

    public User(){}; // empty constructor required by parcel

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.username = jsonObject.getString("name");
        user.screenName = "@" + jsonObject.getString("screen_name");
        user.profileImageUrl = jsonObject.getString(("profile_image_url_https"));



        return user;

    }
}
//"user_mentions":[{"screen_name":"SpaceX","name":"SpaceX","id":34743251,"id_str":"34743251","indices":[3,10]}],"urls":[]},"source":"

