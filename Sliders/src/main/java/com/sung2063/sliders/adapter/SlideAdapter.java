package com.sung2063.sliders.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * The SlideAdapter class helps to connect with ViewPager.
 *
 * @author  Sung Hyun Back
 * @version 1.0
 * @since   2020-07-02
 */
public class SlideAdapter extends PagerAdapter {

    // =============================================================================================
    // Variables
    // =============================================================================================
    private List<ViewGroup> slideList;

    // =============================================================================================
    // Constructors
    // =============================================================================================
    public SlideAdapter(List<ViewGroup> slideList) {
        this.slideList = slideList;
    }

    // =============================================================================================
    // Methods
    // =============================================================================================
    @Override
    public int getCount() {
        return slideList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ViewGroup) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ViewGroup slideLayout = (ViewGroup) slideList.get(position);
        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ViewGroup) object);
    }
}
