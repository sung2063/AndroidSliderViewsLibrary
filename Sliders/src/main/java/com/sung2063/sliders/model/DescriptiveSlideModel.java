package com.sung2063.sliders.model;

import android.view.ViewGroup;

/**
 * Model for creating a descriptive slide
 *
 * @author  Sung Hyun Back
 * @version 1.0
 * @since   2020-07-02
 */
public class DescriptiveSlideModel {

    // =============================================================================================
    // Variables
    // =============================================================================================
    private ViewGroup slide;
    private String subTitle;

    // =============================================================================================
    // Constructor
    // =============================================================================================
    public DescriptiveSlideModel(ViewGroup slide, String subTitle) {
        this.slide = slide;
        this.subTitle = subTitle;
    }

    // =============================================================================================
    // Methods
    // =============================================================================================
    public ViewGroup getSlide() {
        return slide;
    }

    public void setSlide(ViewGroup slide) {
        this.slide = slide;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
