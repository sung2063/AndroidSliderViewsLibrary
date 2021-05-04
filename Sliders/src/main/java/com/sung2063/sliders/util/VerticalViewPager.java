package com.sung2063.sliders.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * Vertical View Pager helps to scroll ViewPager vertically.
 */
public class VerticalViewPager extends ViewPager {

    // =============================================================================================
    // Constructors
    // =============================================================================================
    public VerticalViewPager(Context context) {
        this(context, null);
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    // =============================================================================================
    // Methods
    // =============================================================================================
    @Override
    public boolean canScrollHorizontally(int direction) {
        return false;
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return super.canScrollHorizontally(direction);
    }

    private void init() {
        setPageTransformer(true, new VerticalPageTransformer());
        setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final boolean TO_INTERCEPT = super.onInterceptTouchEvent(flipXY(ev));
        flipXY(ev);
        return TO_INTERCEPT;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final boolean TO_HANDLE = super.onTouchEvent(flipXY(ev));
        flipXY(ev);
        return TO_HANDLE;
    }

    private MotionEvent flipXY(MotionEvent ev) {
        final float WIDTH = getWidth();
        final float HEIGHT = getHeight();
        final float x = (ev.getY() / HEIGHT) * WIDTH;
        final float y = (ev.getX() / WIDTH) * HEIGHT;
        ev.setLocation(x, y);
        return ev;
    }

    private static final class VerticalPageTransformer implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View view, float position) {
            if (position >= -1 && position <= 1) {
                final int PAGE_WIDTH = view.getWidth();
                final int PAGE_HEIGHT = view.getHeight();
                view.setAlpha(1);
                view.setTranslationX(PAGE_WIDTH * -position);
                float yPosition = position * PAGE_HEIGHT;
                view.setTranslationY(yPosition);
            } else {
                view.setAlpha(0);
            }
        }
    }
}
