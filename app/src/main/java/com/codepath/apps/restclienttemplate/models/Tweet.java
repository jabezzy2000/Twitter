package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

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
    public String username;

    public Tweet(){}; //empty constructor required by parceler
    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));

        //Checking whether twitter has truncated the message and returning the full message if it has been cut short
        if(jsonObject.has("full_text")) {
            tweet.body = jsonObject.getString(("full_text"));
        }
        else {
            tweet.body = jsonObject.getString("text");
        }
        //if the tweet has extras, It'll be in entities obj. hence we want to get the image from the entities Object
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
//jsonArray=[{
// "created_at":"Wed Jun 08 23:55:04 +0000 2022","id":1534685474731589632,
// "id_str":"1534685474731589632",
// "full_text":"RT @SpaceX: Falcon 9 launches the Nilesat 301 communications satellite to a geosynchronous transfer orbit from Florida https:\/\/t.co\/HidKNqR…"
// ,"truncated":false,
// "display_text_range":[0,140],
// "entities":{"hashtags":[],"symbols":[],"user_mentions":[{"screen_name":"SpaceX","name":"SpaceX","id":34743251,"id_str":"34743251","indices":[3,10]}],"urls":[]},
// "source":"<a href=\"http:\/\/twitter.com\/download\/iphone\" rel=\"nofollow\">Twitter for iPhone<\/a>",
// "in_reply_to_status_id":null,
// "in_reply_to_status_id_str":null,
// "in_reply_to_user_id":null,"in_reply_to_user_id_str":null,
// "in_reply_to_screen_name":null,
// "user":{"id":44196397,"id_str":"44196397","name":"Elon Musk","screen_name":"elonmusk","location":"","description":"","url":null,"entities":{"description":{"urls":[]}},"protected":false,"followers_count":97215229,"friends_count":112,"listed_count":96452,"created_at":"Tue Jun 02 20:12:29 +0000 2009","favourites_count":13507,"utc_offset":null,"time_zone":null,"geo_enabled":false,"verified":true,"statuses_count":18324,"lang":null,"contributors_enabled":false,"is_translator":false,"is_translation_enabled":false,"profile_background_color":"C0DEED","profile_background_image_url":"http:\/\/abs.twimg.com\/images\/themes\/theme1\/bg.png","profile_background_image_url_https":"https:\/\/abs.twimg.com\/images\/themes\/theme1\/bg.png","profile_background_tile":false,"profile_image_url":"http:\/\/pbs.twimg.com\/profile_images\/1529956155937759233\/Nyn1HZWF_normal.jpg","profile_image_url_https":"https:\/\/pbs.twimg.com\/profile_images\/1529956155937759233\/Nyn1HZWF_normal.jpg","profile_banner_url":"https:\/\/pbs.twimg.com\/profile_banners\/44196397\/1576183471","profile_link_color":"0084B4","profile_sidebar_border_color":"C0DEED","profile_sidebar_fill_color":"DDEEF6","profile_text_color":"333333","profile_use_background_image":true,"has_extended_profile":true,"default_profile":false,"default_profile_image":false,"following":true,"follow_request_sent":false,"notifications":false,"translator_type":"none","withheld_in_countries":[]},"geo":null,"coordinates":null,"place":null,"contributors":null,"retweeted_status":{"created_at":"Wed Jun 08 23:51:22 +0000 2022","id":1534684542304256000,"id_str":"1534684542304256000","full_text":"Falcon 9 launches the Nilesat 301 communications satellite to a geosynchronous transfer orbit from Florida https:\/\/t.co\/HidKNqRXtO","truncated":false,"display_text_range":[0,106],"entities":{"hashtags":[],"symbols":[],"user_mentions":[],"urls":[],"media":[{"id":1534684466127327234,"id_str":"1534684466127327234","indices":[107,130],"media_url":"http:\/\/pbs.twimg.com\/media\/FUxLZA3UUAIAVPj.jpg","media_url_https":"https:\/\/pbs.twimg.com\/media\/FUxLZA3UUAIAVPj.jpg","url":"https:\/\/t.co\/HidKNqRXtO","display_url":"pic.twitter.com\/HidKNqRXtO","expanded_url":"https:\/\/twitter.com\/SpaceX\/status\/1534684542304256000\/photo\/1","type":"photo","sizes":{"large":{"w":1365,"h":2048,"resize":"fit"},"thumb":{"w":150,"h":150,"resize":"crop"},"small":{"w":453,"h":680,"resize":"fit"},"medium":{"w":800,"h":1200,"resize":"fit"}}}]},"extended_entities":{"media":[{"id":1534684466127327234,"id_str":"1534684466127327234","indices":[107,130],"media_url":"http:\/\/pbs.twimg.com\/media\/FUxLZA3UUAIAVPj.jpg","media_url_https":"https:\/\/pbs.twimg.com\/media\/FUxLZA3UUAIAVPj.jpg","url":"https:\/\/t.co\/HidKNqRXtO","display_url":"pic.twitter.com\/HidKNqRXtO","expanded_url":"https:\/\/twitter.com\/SpaceX\/status\/1534684542304256000\/photo\/1","type":"photo","sizes":{"large":{"w":1365,"h":2048,"resize":"fit"},"thumb":{"w":150,"h":150,"resize":"crop"},"small":{"w":453,"h":680,"resize":"fit"},"medium":{"w":800,"h":1200,"resize":"fit"}}},{"id":1534684466148364288,"id_str":"1534684466148364288","indices":[107,130],"media_url":"http:\/\/pbs.twimg.com\/media\/FUxLZA8VUAA8jRf.jpg","media_url_https":"https:\/\/pbs.twimg.com\/media\/FUxLZA8VUA
//

