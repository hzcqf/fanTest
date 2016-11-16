package com.test.fan.fantest.annex;

import android.content.Context;

/**
 * Created by Fan. on 2016/11/16.
 */

public class Service {

    private static Service instance;

    public static synchronized Service getInstance() {

        if (instance == null) {

            instance = new Service();
        }
        return instance;
    }

    public String getImageId(Context context) {
        Preferences preferences = Preferences.from(context);
        return preferences.getImageID();
    }

    public void saveImageID(Context context, String ImageID) {
        Preferences preferences = Preferences.from(context);
        preferences.setImageID(ImageID);
        Preferences.saveOrUpdate(preferences, context);
    }
}
