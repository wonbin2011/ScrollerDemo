package com.wonbin.scollerdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by wonbin on 12/16/16.
 */

public class LauncherViewGroup extends ViewGroup {

    private static final String TAG = "wonbin";

    private Context mContext;
    private Scroller mScroller;

    private int width;
    private int scrollX;
    private int touchSlop;
    private int leftLimit;
    private int rightLimit;

    private float lastX;

    public LauncherViewGroup(Context context) {
        this(context,null);
    }

    public LauncherViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        touchSlop = configuration.getScaledTouchSlop();

        width = getResources().getDisplayMetrics().widthPixels;
        Log.d(TAG," width = " + width + "  touchSlop = " + touchSlop);
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            this.postInvalidate();
        }
//        super.computeScroll();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        for (int i = 0; i < childCount;i++) {
            View childView = getChildAt(i);
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount;i++) {
                View childView = getChildAt(i);
                int left = i * childView.getMeasuredWidth();
                int top = 0;
                int right = (i + 1) * childView.getMeasuredWidth();
                int bottom = childView.getMeasuredHeight();
                childView.layout(left,top,right,bottom);
            }

            leftLimit = getChildAt(0).getLeft();

            rightLimit = getChildAt(childCount - 1).getLeft();
            Log.d(TAG," leftLimit = " + leftLimit +  "  rightLimit = " + rightLimit);

            int scrollX = getScrollX();
            int left =  (getChildCount() - 1) * getWidth() ;
            Log.d(TAG," scrollX = " + scrollX + " left == " + left + " getWidth() = " + getWidth());
        }


    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        float x = ev.getRawX();
//        Log.d("wonbin", "onInterceptTouchEvent  x-- " + x);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                Log.d("wonbin"," onInterceptTouchEvent ACTION_DOWN   lastX = " + lastX);
                return true;
//                break;
            case MotionEvent.ACTION_MOVE:
                float moveDistance = Math.abs(x - lastX);
                Log.d("wonbin"," onInterceptTouchEvent ACTION_MOVE moveDistance = " +moveDistance);
                if(moveDistance > touchSlop) {
                    return true;
                }
                lastX = x;
                break;
            case MotionEvent.ACTION_UP:
                Log.d("wonbin"," onInterceptTouchEvent ACTION_UP" );
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getRawX();
//        Log.d("wonbin", "--onTouchEvent--");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("wonbin", "--onTouchEvent-- ACTION_DOWN ");
                return true;
//                break;
            case MotionEvent.ACTION_MOVE:
                int moveDistance = (int) (lastX - x);

                if(canMove(moveDistance)) {
                    scrollBy(moveDistance,0);
                }
                lastX = x;
                break;
            case MotionEvent.ACTION_UP:
                checkAligment();
                invalidate();
                break;
        }

        return super.onTouchEvent(event);
    }

    private void checkAligment() {
        scrollX = getScrollX();
        int index = (scrollX + width / 2) / width;
        Log.d("wonbin", "--onTouchEvent--  ACTION_UP--" + index);
        int distanceX = width * index - scrollX;
        mScroller.startScroll(scrollX,0,distanceX,0);
    }

    private boolean canMove(int deltaX) {
        scrollX = getScrollX();

        if (scrollX + deltaX < leftLimit) {
            scrollTo(leftLimit,0);
            return false;
        }

        if(scrollX + deltaX > rightLimit) {
            scrollTo(rightLimit,0);
            return false;
        }
        return true;
    }
}
