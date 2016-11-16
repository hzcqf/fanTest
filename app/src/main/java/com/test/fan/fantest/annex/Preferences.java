package com.test.fan.fantest.annex;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

/**
 * Created by Fan. on 2016/11/16.
 */

public class Preferences implements Serializable {
    private String imageID = "";

    public static boolean saveOrUpdate(Preferences setting, Context context) {
        try {
            SharedPreferences prefs = context.getSharedPreferences("zst.prefs",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("imageID", setting.imageID);
            editor.commit();
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public static Preferences from(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("zst.prefs",
                Context.MODE_PRIVATE);
        Preferences setting = new Preferences();
        setting.imageID = prefs.getString("imageID", "");
        return setting;

    }


    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }
}
