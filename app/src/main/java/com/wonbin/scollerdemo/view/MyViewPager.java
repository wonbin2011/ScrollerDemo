package com.wonbin.scollerdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by wonbin on 12/17/16.
 */

public class MyViewPager extends ViewGroup {

    private int mScreenWidth;
    private int mStart;
    private int mEnd;
    private int lastX;

    private Scroller mScroller;


    public MyViewPager(Context context) {
        this(context,null);
        init(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        mScroller = new Scroller(context);

        /*LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        LinearLayout l1 = new LinearLayout(context);
        LinearLayout l2 = new LinearLayout(context);
        LinearLayout l3 = new LinearLayout(context);
        l1.setLayoutParams(lp);
        l2.setLayoutParams(lp);
        l3.setLayoutParams(lp);
        l1.setBackgroundColor(context.getResources().getColor(android.R.color.holo_orange_dark));
        l2.setBackgroundColor(context.getResources().getColor(android.R.color.holo_blue_dark));
        l3.setBackgroundColor(context.getResources().getColor(android.R.color.holo_green_dark));
        addView(l1);
        addView(l2);
        addView(l3);*/

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (changed) {
            int width = 0;
            int childCount = getChildCount();
            for (int i = 0; i < childCount;i++) {

                View childView = getChildAt(i);

                childView.layout(width,0,width + childView.getMeasuredWidth(),childView.getMeasuredHeight());
                width += childView.getMeasuredWidth();
                mScreenWidth = childView.getMeasuredWidth();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                //记录下起始点的位置
                mStart = getScrollX();
                //如果滑动结束，停止动画
                if (mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }

                lastX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = lastX - x;
                if (isMove(deltaX)) {
                    //在滑动范围内，允许滑动
                    scrollBy(deltaX,0);
                }
                lastX = x;
                break;
            case MotionEvent.ACTION_UP:
                //判断滑动的距离
                int distanceX = checkAlignment();

                Log.d("wonbin", " distanceX = " + distanceX);

                //弹性滑动开启
//                if (distanceX > 0) {  //从右向左滑
//                    if (distanceX < mScreenWidth / 2) {
//                        Log.d("wonbin", " distanceX = " + distanceX);
//                        mScroller.startScroll(getScrollX(),0,-distanceX,0,500);
//                    } else {
//                        mScroller.startScroll(getScrollX(),0,mScreenWidth - distanceX,0,500);
//                    }
//                } else {   //从左向右滑
//                    if (-distanceX < mScreenWidth / 2) {
//                        mScroller.startScroll(getScrollX(),0,-distanceX,0,500);
//                    } else {
//                        mScroller.startScroll(getScrollX(),0,-mScreenWidth - distanceX,0,500);
//                    }
//                }

                if (Math.abs(distanceX) < mScreenWidth / 2) {
                    mScroller.startScroll(getScrollX(),0,-distanceX,0,500);
                } else {
                    if (distanceX > 0)
                        mScroller.startScroll(getScrollX(),0,(mScreenWidth -distanceX),0,500);
                    else
                        mScroller.startScroll(getScrollX(),0,-mScreenWidth - distanceX,0,500);
                }

                break;
        }
        invalidate();
        return true;
    }

    private int checkAlignment(){
        //判断滑动的趋势，向左滑 还是向右滑
        mEnd = getScrollX();

        // >0  from left  to right true left 从右向左滑
        boolean isUp = ((mEnd - mStart) > 0);

        int lastPrev = mEnd % mScreenWidth;
        int lastNext = mScreenWidth - lastPrev;
        if (isUp) {   // mEnd - mStart 大于零 从右向左滑 <----
            return lastPrev;
        } else {    // mEnd - mStart 小于灵 从左向右滑 ---->
            return -lastNext;
        }
    }

    private boolean isMove(int deltaX) {
        int scrollX = getScrollX();

        //滑动到第一屏，不能在向右滑动了
        if (deltaX < 0) { // 从左向右滑动

            if (scrollX < 0) {
                return false;
            } else if (deltaX + scrollX < 0) {
                scrollTo(0,0);
                return false;
            }
        }

        int leftLimit = (getChildCount() - 1) * getWidth();

        if (deltaX > 0) { //从右向左划
            if (scrollX >= leftLimit) {
                return false;
            } else if (scrollX + deltaX > leftLimit) {
                scrollTo(leftLimit,0);
                return false;
            }
        }

        return true;
    }

}
