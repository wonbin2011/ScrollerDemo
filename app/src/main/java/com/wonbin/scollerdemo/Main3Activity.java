package com.wonbin.scollerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Main3Activity extends AppCompatActivity {

    private static final String TAG = "wonbin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bounce);
        Log.d(TAG," onCreate");
        setContentView(R.layout.layout_launcher);

//        setContentView(R.layout.layout_viewpager);
    }
}
