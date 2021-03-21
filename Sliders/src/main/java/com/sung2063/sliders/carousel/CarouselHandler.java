package com.sung2063.sliders.carousel;

import android.content.Context;
import android.view.ViewGroup;

import com.sung2063.sliders.R;
import com.sung2063.sliders.exceptions.SlideOutOfBoundException;

import java.util.List;

/**
 * The CarouselHandler class helps stores the data of CarouselView.
 *
 * @author  Sung Hyun Back
 * @version 1.0
 * @since   2020-07-02
 */
public class CarouselHandler {

    // =============================================================================================
    // Variables
    // =============================================================================================
    private Context context;
    private List<ViewGroup> slideList;
    private int scrollDirection;
    private boolean isShowingIndicator;
    private boolean isShowingSlideNumber;

    // =============================================================================================
    // Fields
    // =============================================================================================
    /**
     * Constant value for Carousel horizontal direction
     */
    public static final int CAROUSEL_HORIZONTAL_DIRECTION = 0;

    /**
     * Constant value for Carousel vertical direction
     */
    public static final int CAROUSEL_VERTICAL_DIRECTION = 1;

    // =============================================================================================
    // Constructors
    // =============================================================================================
    public CarouselHandler() {
        // Default Constructor
    }

    public CarouselHandler(Context context, int scrollDirection, boolean isShowingIndicator, boolean isShowingSlideNumber) {
        this.context = context;
        this.scrollDirection = scrollDirection;
        this.isShowingIndicator = isShowingIndicator;
        this.isShowingSlideNumber = isShowingSlideNumber;
    }

    // =============================================================================================
    // Methods
    // =============================================================================================

    /**
     * Returns the list of ViewGroup slide
     * @return the list of ViewGroup
     */
    public List<ViewGroup> getSlideList() {
        return slideList;
    }

    /**
     * Set slide list
     * @param slideList List of ViewGroup slide which user created
     * @throws SlideOutOfBoundException on list size is greater than 10
     */
    public void setSlideList(List<ViewGroup> slideList) throws SlideOutOfBoundException {
        if (slideList.size() > 10) {
            throw new SlideOutOfBoundException(context.getString(R.string.exceed_slide_boundary_error));
        }
        this.slideList = slideList;
    }

    /**
     * Returns the scroll direction
     * @return 0 for horizontal direction or 1 for vertical direction
     */
    public int getScrollDirection() {
        return scrollDirection;
    }

    /**
     * Set scroll direction
     * @param scrollDirection scroll direction either horizontal or vertical in integer
     */
    public void setScrollDirection(int scrollDirection) {
        this.scrollDirection = scrollDirection;
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
     * Returns the value of showing slide number
     * @return true if showing the slide number, otherwise false
     */
    public boolean isShowingSlideNumber() {
        return isShowingSlideNumber;
    }

    /**
     * Set the value of slide number
     * @param isShowingSlideNumber boolean value for showing the slide number
     */
    public void showSlideNumber(boolean isShowingSlideNumber) {
        this.isShowingSlideNumber = isShowingSlideNumber;
    }

}
