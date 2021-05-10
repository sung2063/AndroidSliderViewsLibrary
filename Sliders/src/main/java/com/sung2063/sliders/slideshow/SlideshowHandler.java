package com.sung2063.sliders.slideshow;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;

import com.sung2063.sliders.R;
import com.sung2063.sliders.exceptions.IllegalArgumentException;
import com.sung2063.sliders.exceptions.SlideOutOfBoundException;
import com.sung2063.sliders.model.DescriptiveSlideModel;

import java.util.List;

/**
 * The SlideshowHandler class helps stores the data of SlideshowView.
 *
 * @author Sung Hyun Back
 * @version 1.0
 * @since 2020-07-02
 */
public class SlideshowHandler {

    // =============================================================================================
    // Variables
    // =============================================================================================
    private Context context;
    private List<ViewGroup> slideList;
    private List<DescriptiveSlideModel> descriptiveSlideList;
    private boolean isShowingIndicator;
    private float indicatorScale;
    private Drawable indicatorSelectedIcon;
    private Drawable indicatorUnselectedIcon;
    private boolean isShowingSlideNumber;
    private int slideNumberTextSize;        // in px
    private int delayTimePeriod;
    private boolean isShowingSubTitle;

    // =============================================================================================
    // Constructors
    // =============================================================================================
    public SlideshowHandler() {
        // Default Constructor
    }

    public SlideshowHandler(Context context, boolean isShowingIndicator, float indicatorScale, Drawable indicatorSelectedIcon, Drawable indicatorUnselectedIcon, boolean isShowingSlideNumber, int slideNumberTextSize, int delayTimePeriod, boolean isShowingSubTitle) {
        this.context = context;
        this.isShowingIndicator = isShowingIndicator;
        this.indicatorScale = indicatorScale;
        this.indicatorSelectedIcon = indicatorSelectedIcon;
        this.indicatorUnselectedIcon = indicatorUnselectedIcon;
        this.isShowingSlideNumber = isShowingSlideNumber;
        this.slideNumberTextSize = slideNumberTextSize;
        this.delayTimePeriod = delayTimePeriod;
        this.isShowingSubTitle = isShowingSubTitle;
    }

    // =============================================================================================
    // Methods
    // =============================================================================================

    /**
     * Returns the list of ViewGroup slide
     *
     * @return the list of ViewGroup
     */
    protected List<ViewGroup> getSlideList() {
        return slideList;
    }

    /**
     * Returns the list of descriptive slide
     *
     * @return the list of descriptive slide
     */
    public List<DescriptiveSlideModel> getDescriptiveSlideList() {
        return descriptiveSlideList;
    }

    /**
     * Set slide list
     *
     * @param slideList List of ViewGroup slide which user created
     * @throws SlideOutOfBoundException on list size is greater than 10
     */
    protected void setSlideList(List<ViewGroup> slideList) throws SlideOutOfBoundException {
        if (slideList.size() > 10) {
            throw new SlideOutOfBoundException(context.getString(R.string.exceed_slide_boundary_error));
        }
        this.slideList = slideList;
    }

    /**
     * Set descriptive slide list
     *
     * @param descriptiveSlideList List of descriptive slide which user created
     * @throws SlideOutOfBoundException on list size is greater than 10
     */
    public void setDescriptiveSlideList(List<DescriptiveSlideModel> descriptiveSlideList) throws SlideOutOfBoundException {
        if (descriptiveSlideList.size() > 10) {
            throw new SlideOutOfBoundException(context.getString(R.string.exceed_slide_boundary_error));
        }
        this.descriptiveSlideList = descriptiveSlideList;
    }

    /**
     * Returns the value of showing tab indicator
     *
     * @return true if showing the tab indicator, otherwise false
     */
    protected boolean isShowingIndicator() {
        return isShowingIndicator;
    }

    /**
     * Set the value of tab indicator
     *
     * @param isShowingIndicator boolean value for showing the tab indicator
     */
    protected void showIndicator(boolean isShowingIndicator) {
        this.isShowingIndicator = isShowingIndicator;
    }

    /**
     * Returns the value of indicator scale
     *
     * @return indicatorScale returns the indicator scale value
     */
    protected float getIndicatorScale() {
        return indicatorScale;
    }

    /**
     * Set the value of indicator scale
     *
     * @param indicatorScale float value for indicator scale
     */
    protected void setIndicatorScale(float indicatorScale) {
        this.indicatorScale = indicatorScale;
    }

    /**
     * Returns the drawable id of indicator selected icon
     *
     * @return indicatorSelectedIcon returns indicator selected icon drawable id
     */
    protected Drawable getIndicatorSelectedIcon() {
        return indicatorSelectedIcon;
    }

    /**
     * Set the drawable id of indicator selected icon
     *
     * @param indicatorSelectedIcon drawable id of indicator selected icon
     */
    protected void setIndicatorSelectedIcon(Drawable indicatorSelectedIcon) {
        this.indicatorSelectedIcon = indicatorSelectedIcon;
    }

    /**
     * Returns the drawable id of indicator unselected icon
     *
     * @return indicatorUnselectedIcon returns indicator unselected icon drawable id
     */
    protected Drawable getIndicatorUnselectedIcon() {
        return indicatorUnselectedIcon;
    }

    /**
     * Set the drawable id of indicator unselected icon
     *
     * @param indicatorUnselectedIcon drawable id of indicator unselected icon
     */
    protected void setIndicatorUnselectedIcon(Drawable indicatorUnselectedIcon) {
        this.indicatorUnselectedIcon = indicatorUnselectedIcon;
    }

    /**
     * Returns the value of slide delay time period
     *
     * @return slide delay time in second
     */
    protected long getDelayTimePeriod() {
        return delayTimePeriod;
    }

    /**
     * Set the value of slide delay time period
     *
     * @param delayTimePeriod slide delay time in second
     * @throws IllegalArgumentException on delay time is set less than 0 or greater than 10
     */
    protected void setDelayTimePeriod(int delayTimePeriod) throws IllegalArgumentException {
        if (delayTimePeriod < 0 || delayTimePeriod > 10) {
            throw new IllegalArgumentException(context.getString(R.string.slide_delay_time_illegal_error));
        }
        this.delayTimePeriod = delayTimePeriod;
    }

    /**
     * Returns the value of showing slide number
     *
     * @return true if showing the slide number, otherwise false
     */
    protected boolean showSlideNumber() {
        return isShowingSlideNumber;
    }

    /**
     * Set the value of slide number
     *
     * @param isShowingSlideNumber boolean value for showing the slide number
     */
    protected void setShowingSlideNumber(boolean isShowingSlideNumber) {
        this.isShowingSlideNumber = isShowingSlideNumber;
    }

    /**
     * Get the value of slide number text size in px
     *
     * @return slide number text size in px
     */
    public int getSlideNumberTextSize() {
        return slideNumberTextSize;
    }

    /**
     * Set the value of slide number text size in px
     *
     * @param slideNumberTextSize int value for slide number text size in px
     */
    public void setSlideNumberTextSize(int slideNumberTextSize) {
        this.slideNumberTextSize = slideNumberTextSize;
    }

    /**
     * Get subtitle showing value
     *
     * @return boolean value for subTitle visibility
     */
    public boolean isShowingSubTitle() {
        return isShowingSubTitle;
    }

    /**
     * Set subtitle visibility
     *
     * @param isShowingSubTitle boolean value if subtitle should be shown
     */
    public void setShowingSubTitle(boolean isShowingSubTitle) {
        this.isShowingSubTitle = isShowingSubTitle;
    }
}
