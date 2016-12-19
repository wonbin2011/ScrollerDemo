package com.wonbin.scollerdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * Created by wonbin on 12/16/16.
 */

public class BounceableRelativeLayout extends RelativeLayout {

    //private static final String TAG = BounceableRelativeLayout.class.getSimpleName();
    private static final String TAG = "wonbin";

    private Scroller mScroller;
    private GestureDetector mGestureDetector;

    public BounceableRelativeLayout(Context context) {

        this(context,null);
    }

    public BounceableRelativeLayout(Context context, AttributeSet attrs) {
        super(context,attrs);
        setClickable(true);
        setLongClickable(true);
        mScroller = new Scroller(context);
        mGestureDetector = new GestureDetector(context,new GestureListenerImpl());
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            this.postInvalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                reset(0,0);
                Log.d(TAG, " ACTION UP");
                break;
            default:
                return mGestureDetector.onTouchEvent(event);
        }

        return super.onTouchEvent(event);
    }

    class GestureListenerImpl implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(TAG, " onDown event = " + e);
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int disY = (int) ((distanceY - 0.5) /2);
            Log.d(TAG,"onScroll distanceY =" + disY);
            beginScroll(0,disY);
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

    protected void reset(int x, int y) {
        int dx = x - mScroller.getFinalX();
        int dy = x - mScroller.getFinalY();
        Log.d(TAG,"reset dx = "  + dx + "- dy = " +dy );
        beginScroll(dx,dy);
    }

    protected void beginScroll(int dx,int dy) {
        mScroller.startScroll(mScroller.getFinalX(),mScroller.getFinalY(),dx,dy);
        this.invalidate();
    }
}
