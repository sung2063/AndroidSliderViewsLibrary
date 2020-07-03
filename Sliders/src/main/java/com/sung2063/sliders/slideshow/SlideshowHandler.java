package com.sung2063.sliders.slideshow;

import android.content.Context;
import android.view.ViewGroup;

import com.sung2063.sliders.R;
import com.sung2063.sliders.exceptions.IllegalArgumentException;
import com.sung2063.sliders.exceptions.SlideOutOfBoundException;

import java.util.List;

/**
 * The SlideshowHandler class helps stores the data of SlideshowView.
 *
 * @author  Sung Hyun Back
 * @version 1.0
 * @since   2020-07-02
 */
public class SlideshowHandler {

    // =============================================================================================
    // Variables
    // =============================================================================================
    private Context context;
    private List<ViewGroup> slideList;
    private boolean isShowingIndicator;
    private boolean isShowingSlideNumber;
    private int delayTimePeriod;

    // =============================================================================================
    // Constructors
    // =============================================================================================
    public SlideshowHandler() {
        // Default Constructor
    }

    public SlideshowHandler(Context context, boolean isShowingIndicator, boolean isShowingSlideNumber, int delayTimePeriod) {
        this.context = context;
        this.isShowingIndicator = isShowingIndicator;
        this.isShowingSlideNumber = isShowingSlideNumber;
        this.delayTimePeriod = delayTimePeriod;
    }

    // =============================================================================================
    // Methods
    // =============================================================================================

    /**
     * Returns the list of ViewGroup slide
     * @return the list of ViewGroup
     */
    protected List<ViewGroup> getSlideList() {
        return slideList;
    }

    /**
     * Set slide list
     * @param slideList List of ViewGroup slide which user created
     * @throws SlideOutOfBoundException on list size is greater than 10
     */
    protected void setSlideList(List<ViewGroup> slideList) throws SlideOutOfBoundException {
        if (slideList.size() > 10)
            throw new SlideOutOfBoundException(context.getString(R.string.exceed_slide_boundary_error));
        this.slideList = slideList;
    }

    /**
     * Returns the value of showing tab indicator
     * @return true if showing the tab indicator, otherwise false
     */
    protected boolean isShowingIndicator() {
        return isShowingIndicator;
    }

    /**
     * Set the value of tab indicator
     * @param isShowingIndicator boolean value for showing the tab indicator
     */
    protected void showIndicator(boolean isShowingIndicator) {
        this.isShowingIndicator = isShowingIndicator;
    }

    /**
     * Returns the value of slide delay time period
     * @return slide delay time in second
     */
    protected long getDelayTimePeriod() {
        return delayTimePeriod;
    }

    /**
     * Set the value of slide delay time period
     * @param delayTimePeriod slide delay time in second
     * @exception IllegalArgumentException on delay time is set less than 0 or greater than 10
     */
    protected void setDelayTimePeriod(int delayTimePeriod) throws IllegalArgumentException {
        if ((delayTimePeriod < 0) || (delayTimePeriod > 10))
            throw new IllegalArgumentException(context.getString(R.string.slide_delay_time_illegal_error));
        this.delayTimePeriod = delayTimePeriod;
    }

    /**
     * Returns the value of showing slide number
     * @return true if showing the slide number, otherwise false
     */
    protected boolean showSlideNumber() {
        return isShowingSlideNumber;
    }

    /**
     * Set the value of slide number
     * @param isShowingSlideNumber boolean value for showing the slide number
     */
    protected void setShowingSlideNumber(boolean isShowingSlideNumber) {
        this.isShowingSlideNumber = isShowingSlideNumber;
    }
}
