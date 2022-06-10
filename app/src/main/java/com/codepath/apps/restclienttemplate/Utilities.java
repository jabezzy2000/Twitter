package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import androidx.core.net.ParseException;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Headers;

public class Utilities {
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    List<Tweet> tweets;
    TweetsAdapter adapter;

    public static void setImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).into(iv);
    } // method to setImage

    public static void roundedImage(Context context, String url, ImageView iv, int radius) {
        Glide.with(context).load(url).transform(new RoundedCorners(radius)).into(iv);
    } // method to setImage with rounded corners

    //method to get the date/time of a tweet in friendly format
    public static String getsimpletime(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        try {
            long time = sf.parse(rawJsonDate).getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d";
            }
        } catch (ParseException | java.text.ParseException e) {
            Log.i("parsetag", "getRelativeTimeAgo failed");
            e.printStackTrace();
        }

        return "";
    }


//    JsonHttpResponseHandler JSONHandlerVariable = new JsonHttpResponseHandler() {
//        @Override
//        public void onSuccess(int statusCode, Headers headers, JsonHttpResponseHandler.JSON json) {
//            Log.i("TAG", "onSuccess: Success " + json.toString());
//            JSONArray jsonArray = json.jsonArray;
//            try {
//                tweets.addAll(Tweet.fromJsonArray(jsonArray));
//                adapter.notifyDataSetChanged();
//            } catch (JSONException e) {
//                Log.e("TAG", "Json exception",e );
//                e.printStackTrace();
//            }
//
//
//        }
//
//        @Override
//        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
//            Log.e("TAG", "onFailure: here" + response, throwable );
////                loge + response as in above shows the reason why youre having an error..good for debugging
//
//        } // ask why this doesnt work
//    };
}

