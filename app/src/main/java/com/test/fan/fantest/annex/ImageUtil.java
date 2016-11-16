package com.test.fan.fantest.annex;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Fan. on 2016/11/16.
 */

public class ImageUtil {

    /**
     * 取得图片压缩并保存
     */
    public static String compressImageAndSave(Context context, String path) {
        Bitmap bitmap = getimage(path);
        return saveImg(context, bitmap);
    }

    /**
     * 保存照片
     */
    private static String saveImg(Context context, Bitmap b) {
        String guid = "";
        SimpleDateFormat ddd = new SimpleDateFormat("yyyyMMddhhmmssSSS",
                Locale.CHINA);
        guid = Service.getInstance().getImageId(context);
        String name = guid + ddd.format(Calendar.getInstance(Locale.CHINA).getTime());
        String mImageFilePath = AnnexUtil.getImageRootPath(context) + name
                + ".jpg";
        // 清空重复文件
        File mediaFile = new File(mImageFilePath);
        if (mediaFile.exists()) {
            mediaFile.delete();

        }
        // 建设目录
        if (!new File(AnnexUtil.getImageRootPath(context)).exists()) {
            new File(AnnexUtil.getImageRootPath(context)).mkdirs();
        }

        try {
            mediaFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(mediaFile);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            b.recycle();
            b = null;
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return mediaFile.getPath();
    }

    /**
     * 获取图片内容
     */
    private static Bitmap getimage(String srcPath) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww) + 1;
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh) + 1;
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 压缩图片
     */
    private static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        System.out.println("什么情况怎么没压缩啊:" + options + "   " + baos.toByteArray().length / 1024 + "  " + baos.size() + "   " +
                getBitmapSize(image));
        while (baos.size() / 1024 > 300) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
            System.out.println("什么情况怎么没压缩啊:" + options + "   " + baos.toByteArray().length / 1024 + "  " + baos.size() + "   " +
                    getBitmapSize(image));
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }


    @SuppressLint("NewApi")
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }
}
