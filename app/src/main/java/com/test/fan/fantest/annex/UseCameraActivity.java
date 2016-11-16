package com.test.fan.fantest.annex;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.test.fan.fantest.R;

import java.io.File;

public class UseCameraActivity extends AppCompatActivity {
    private String TAG = UseCameraActivity.class.getSimpleName();
    private String mImageFilePath;
    private String tmpPath;
    private String CAMERA_PATH = "camera_path";
    static Activity mContext;
    public final static int GET_IMAGE_REQ = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mImageFilePath = savedInstanceState.getString(CAMERA_PATH);
//            Log.i(TAG, mImageFilePath);
            File mFile = new File(mImageFilePath);
            if (mFile.exists()) {
                Intent rsl = new Intent();
                rsl.putExtra(CAMERA_PATH, mImageFilePath);
                setResult(Activity.RESULT_OK, rsl);
                Log.i(TAG, "文件存在");
                finish();
            } else {
                Log.i(TAG, "文件不存在");
            }
        }

        mContext = this;
//		applicationContext = getApplicationContext();
        if (savedInstanceState == null) {
            initialUI();
        }

    }

    public void initialUI() {
        tmpPath = AnnexUtil.getTmpPath(getApplicationContext()) + "tmp.jpg";
        File out = new File(tmpPath);
        if (out.exists()) {
            out.delete();
        }
        showCamera(out);

    }

    private void showCamera(File out) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); // set
        startActivityForResult(intent, GET_IMAGE_REQ);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (GET_IMAGE_REQ == requestCode && resultCode == Activity.RESULT_OK) {
            //保存压缩图片
            mImageFilePath = ImageUtil.compressImageAndSave(getApplicationContext(), tmpPath);
            Intent rsl = new Intent();
//            rsl.putExtra(IMAGE_PATH, mImageFilePath);
            rsl.putExtra(CAMERA_PATH, mImageFilePath);
            mContext.setResult(Activity.RESULT_OK, rsl);
            mContext.finish();

        } else {
            mContext.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("ImageFilePath", mImageFilePath + "");

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }
}
