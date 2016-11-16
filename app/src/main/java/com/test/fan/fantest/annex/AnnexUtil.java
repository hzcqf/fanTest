package com.test.fan.fantest.annex;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by Fan. on 2016/11/16.
 */

public class AnnexUtil {

    /**
     * 获取根目录相关
     *
     * @param context
     * @return
     */
    public static String getAppRootPath(Context context) {
        String filePath = "/Dayu";
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            filePath = Environment.getExternalStorageDirectory() + filePath;
        } else {
            filePath = context.getCacheDir() + filePath;
        }
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = null;
        File nomedia = new File(filePath + "/.nomedia");
        if (!nomedia.exists())
            try {
                nomedia.createNewFile();
            } catch (IOException e) {
            }
        return filePath;
    }

    /**
     * 获取图片目录
     *
     * @param context
     * @return
     */
    public static String getTmpPath(Context context) {
        String filePath = getAppRootPath(context) + "/images/Tmp/";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = null;
        return filePath;
    }

    /**
     * 获取图片目录
     *
     * @param context
     * @return
     */
    public static String getImageRootPath(Context context) {
        String filePath = getAppRootPath(context) + "/images/";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = null;
        return filePath;
    }
}
