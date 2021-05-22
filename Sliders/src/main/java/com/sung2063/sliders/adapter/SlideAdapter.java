package com.sung2063.sliders.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.sung2063.sliders.model.DescriptiveSlideModel;

import java.util.List;

/**
 * The SlideAdapter class helps to connect with ViewPager.
 *
 * @author Sung Hyun Back
 * @version 1.0
 * @since 2020-07-02
 */
public class SlideAdapter extends PagerAdapter {

    // =============================================================================================
    // Variables
    // =============================================================================================
    private List<ViewGroup> slideList;
    private List<DescriptiveSlideModel> descriptiveSlideList;
    private EventListener listener;

    // =============================================================================================
    // Constructors
    // =============================================================================================
    public SlideAdapter(List<ViewGroup> slideList, List<DescriptiveSlideModel> descriptiveSlideList, EventListener listener) {
        if (slideList != null) {
            this.slideList = slideList;
        } else {
            this.descriptiveSlideList = descriptiveSlideList;
        }
        this.listener = listener;
    }

    // =============================================================================================
    // Methods
    // =============================================================================================
    @Override
    public int getCount() {
        if (slideList != null) {
            return slideList.size();
        } else {
            return descriptiveSlideList.size();
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ViewGroup slideLayout;
        if (slideList != null) {
            slideLayout = slideList.get(position);
        } else {
            slideLayout = descriptiveSlideList.get(position).getSlide();
        }

        slideLayout.setOnClickListener(view -> listener.onSlideClicked(position));

        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ViewGroup) object);
    }

    public interface EventListener {
        void onSlideClicked(int position);
    }
}
