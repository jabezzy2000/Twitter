package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Utilities {
    public static void setImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).into(iv);
    }
}
