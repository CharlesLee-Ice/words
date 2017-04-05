package com.learning.words;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.learning.words.behavior.ViewOffsetHelper;

/**
 * Created by liqilin on 2017/2/28.
 */

public class HeaderView extends LinearLayout {
    WordDetailActivity mActivity;

    int displayHeight;
    int displayWidth;
    int mOriginHeight;
    int mOriginWidth;

    int mOriginTop;
    int mOriginBottom;

    private Scroller mScroller;

    public HeaderView(Context context) {
        super(context);

        mActivity = (WordDetailActivity) context;
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mActivity = (WordDetailActivity) context;
    }

    public void init() {
        mOriginHeight =  getHeight();
        mOriginWidth = getWidth();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Point size = new Point();
            mActivity.getWindowManager().getDefaultDisplay().getRealSize(size);
            displayWidth = size.x;
            displayHeight = size.y;
        } else {
            Display display = mActivity.getWindowManager().getDefaultDisplay();
            displayWidth = display.getWidth();
            displayHeight = display.getHeight();
        }

        mOriginTop = getTop();
        mOriginBottom = getBottom();

        mScroller = new Scroller(mActivity, new OvershootInterpolator());

        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

        if (mViewOffsetHelper == null) {
            mViewOffsetHelper = new ViewOffsetHelper(this);
        }
        mViewOffsetHelper.onViewLayout();
    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (mOriginWidth == 0) {
            init();
        }
    }

    private boolean canScroll = true;

    private static final int INVALID_POINTER = -1;
    private boolean mIsBeingDragged;
    private int mMotionX;
    private int mMotionY;
    private int mActivePointerId = INVALID_POINTER;

    private int mTouchSlop = -1;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mIsBeingDragged = false;
                int x = (int) ev.getX();
                int y = (int) ev.getY();

                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mMotionX = x;
                mMotionY = y;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int activePointerId = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                if (activePointerId == INVALID_POINTER) {
                    break;
                }

                int x = (int) MotionEventCompat.getX(ev, activePointerId);
                int y = (int) MotionEventCompat.getY(ev, activePointerId);
                if (Math.abs(y - mMotionY) > Math.abs(x - mMotionX) && Math.abs(y - mMotionY) > mTouchSlop) {
                    mIsBeingDragged = true;
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                break;
        }

        return mIsBeingDragged;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                int activePointerId = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                if (activePointerId == INVALID_POINTER) {
                    break;
                }

                int y = (int) MotionEventCompat.getY(ev, activePointerId);
                int dy = y - mMotionY;

                if (mIsBeingDragged) {
                    scrollY(dy);
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                restoreOrContinue(getTop());

                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                break;
            }
        }

        return true;
    }

    private ViewOffsetHelper mViewOffsetHelper;

    private void scrollY(int dy) {
        int newOffset = mViewOffsetHelper.getTopAndBottomOffset() + dy;

        if (newOffset < -mOriginTop) {
            newOffset = -mOriginTop;
        }
        if (newOffset > 0) {
            newOffset = 0;
        }
        mViewOffsetHelper.setTopAndBottomOffset(newOffset);

        /***
         * 这里不能直接scaleX scaleY 放大Head,因为会同时放大里面的文字
         * 也不能 setLayoutParams 设置宽高，view pager中间一个页面会出现便宜
         */
        final float radio = -(float) newOffset / mOriginTop;
        if (mScrollListener != null) {
            mScrollListener.onScrollFraction(radio);
        }
    }

    private void restoreOrContinue(int currY) {
//        if (currY < 400 && currY > 0) {
//            mScroller.startScroll(0, mViewOffsetHelper.getTopAndBottomOffset(), 0, -currY, 1000);
//            invalidate();
//        }
    }

//    @Override
//    public void computeScroll() {
//        if (!mScroller.isFinished() && mScroller.computeScrollOffset()) {
//            int currY = mScroller.getCurrY();
//
//            float radio = -(float)currY / mOriginTop;
////            scaleHeader(radio);
//            Log.d("liqilin", "radio: "+radio+" currY: "+currY);
//
//            Log.d("liqilin", "computeScroll y: "+currY);
//            mViewOffsetHelper.setTopAndBottomOffset(currY);
//
//            // Keep on drawing until the animation has finished.
//            ViewCompat.postInvalidateOnAnimation(this);
//        }
//    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        /** 外界原因触发重新layout时，需要设置view的准确位置**/
        if (mViewOffsetHelper != null) {
            mViewOffsetHelper.onViewLayout();
        }
    }

    private OnHeadScroll mScrollListener;

    public void setHeadScrollListener(OnHeadScroll listener) {
        mScrollListener = listener;
    }

    public interface OnHeadScroll {
        void onScrollFraction(float fraction);
    }
}
