package com.wonbin.scollerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wonbin.scollerdemo.view.MyLinearLayout;

public class Main2Activity extends AppCompatActivity {

    private Button button;
    private MyLinearLayout myLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sencond);
        button = (Button) findViewById(R.id.button);
        myLinearLayout = (MyLinearLayout) findViewById(R.id.mylayout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myLinearLayout.beginScroll();
            }
        });
    }


}
