package com.wonbin.scollerdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by wonbin on 12/15/16.
 */

public class MyLinearLayout extends LinearLayout {

    private static final String TAG = MyLinearLayout.class.getSimpleName();

    private Scroller mScroller;
    private int offsetY;
    private int duration;

    private boolean flag = true;

    public MyLinearLayout(Context context) {
        this(context,null);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        duration = 10000;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            this.scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            Log.d(TAG," CurX ==" + mScroller.getCurrX() + "  CurY = " + mScroller.getCurrY());
//            Log.d(TAG," view  getScrollX = " + getScaleY() + "  getScrollY = " + getScrollY());
            this.postInvalidate();
        }
    }

    public void  beginScroll() {
        if (flag) {
            offsetY = -100;
            int startX = -300;
            int startY = -90;
            int dx = -500;
            int dy = 0;
            mScroller.startScroll(startX,startY,dx,offsetY,duration);
            flag = false;
        } else {
            offsetY = 0;
            int startX = mScroller.getCurrY();
            int startY = mScroller.getCurrY();
            int dx = -startX;
            int dy = 0;
            mScroller.startScroll(startX,startY,dx,dy,duration);
            flag = true;
        }
        this.invalidate();
    }
}
