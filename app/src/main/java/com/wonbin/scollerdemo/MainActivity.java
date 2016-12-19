package com.wonbin.scollerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private static final String TAG = "wonbin";

    private Button button1;
    private Button button2;
    private Button button3;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.first);
        button2 = (Button) findViewById(R.id.second);
        button3 = (Button) findViewById(R.id.third);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);

        textView = (TextView) findViewById(R.id.text);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.first:
                textView.scrollBy(30,0);
                float scrollX = textView.getScrollX();
                float scrollY = textView.getScrollY();
                Log.d(TAG,"scrollBy:scrollX = " + scrollX + "  scrollY = " + scrollY);
                Log.d(TAG,"Text ==" + textView.getText().toString());
                break;
            case R.id.second:
                textView.scrollTo(-300,0);
                Log.d(TAG,"scrollTo:scrollX = " + textView.getScrollX() + "  scrollY = " + textView.getScrollY());
                break;
            case R.id.third:
                textView.scrollTo(0,0);
        }
    }
}
