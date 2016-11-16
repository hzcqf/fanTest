package com.test.fan.fantest;


import android.app.Activity;
import android.os.Bundle;

import com.test.fan.fantest.R;
import com.test.fan.fantest.RxJava.HttpHelper;
import com.test.fan.fantest.RxJava.HttpRxHelper;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }




}
