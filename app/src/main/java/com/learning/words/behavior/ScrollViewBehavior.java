package com.learning.words.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.learning.words.HeaderView;

/**
 * Created by liqilin on 2017/3/31.
 */

public class ScrollViewBehavior extends CoordinatorLayout.Behavior<View> {

    private int mOriginTop;

    public ScrollViewBehavior() {}

    public ScrollViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof HeaderView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        ViewCompat.offsetTopAndBottom(child, dependency.getBottom() - child.getTop());

        int top = dependency.getTop();
        float ratio = top / (float) mOriginTop;

        child.setAlpha(1.0f - ratio);
        return false;
    }

    public void setDependencyTop(int top) {
        mOriginTop = top;
    }
}
