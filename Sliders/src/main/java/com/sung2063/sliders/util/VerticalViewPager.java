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
        final boolean toIntercept = super.onInterceptTouchEvent(flipXY(ev));
        flipXY(ev);
        return toIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final boolean toHandle = super.onTouchEvent(flipXY(ev));
        flipXY(ev);
        return toHandle;
    }

    private MotionEvent flipXY(MotionEvent ev) {
        final float width = getWidth();
        final float height = getHeight();
        final float x = (ev.getY() / height) * width;
        final float y = (ev.getX() / width) * height;
        ev.setLocation(x, y);
        return ev;
    }

    private static final class VerticalPageTransformer implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View view, float position) {
            final int pageWidth = view.getWidth();
            final int pageHeight = view.getHeight();
            if (position < -1) {
                view.setAlpha(0);
            } else if (position <= 1) {
                view.setAlpha(1);
                view.setTranslationX(pageWidth * -position);
                float yPosition = position * pageHeight;
                view.setTranslationY(yPosition);
            } else {
                view.setAlpha(0);
            }
        }
    }
}
