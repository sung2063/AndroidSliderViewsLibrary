package com.sung2063.sliders.slideshow;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sung2063.sliders.R;
import com.sung2063.sliders.adapter.SlideAdapter;
import com.sung2063.sliders.exceptions.IllegalArgumentException;
import com.sung2063.sliders.exceptions.SlideNullPointerException;
import com.sung2063.sliders.exceptions.SlideOutOfBoundException;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The SlideshowView is the view which user can create the slideshow with custom layouts.
 *
 * @author  Sung Hyun Back
 * @version 1.0
 * @since   2020-07-02
 */
public class SlideshowView extends LinearLayout {

    // =============================================================================================
    // Variables
    // =============================================================================================
    private Context context;
    private ViewPager vpSlider;
    private TabLayout tabIndicator;
    private TextView tvPageNum;
    private SlideshowHandler slideshowHandler;

    // =============================================================================================
    // Constructors
    // =============================================================================================
    // Create object by programmatically
    public SlideshowView(Context context) {
        super(context);
        this.context = context;
        slideshowHandler = new SlideshowHandler();
        init(context);
    }

    // Create object from XML file
    public SlideshowView(Context context, @Nullable AttributeSet attrs) throws IllegalArgumentException {
        super(context, attrs);

        this.context = context;
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlideshowView, 0, 0);
        try {
            boolean isShowingIndicator = typedArray.getBoolean(R.styleable.SlideshowView_showIndicator, false);
            boolean isShowingSlideNumber = typedArray.getBoolean(R.styleable.SlideshowView_showSlideNumber, false);
            int delayTimePeriod = typedArray.getInt(R.styleable.SlideshowView_delayTimePeriod, 5);

            // Check illegal exception
            if ((delayTimePeriod < 0) || (delayTimePeriod > 10))
                throw new IllegalArgumentException(context.getString(R.string.slide_delay_time_illegal_error));

            slideshowHandler = new SlideshowHandler(context, isShowingIndicator, isShowingSlideNumber, delayTimePeriod);

        } finally {
            typedArray.recycle();
            init(context);
        }
    }

    // =============================================================================================
    // Methods
    // =============================================================================================
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    /**
     * Initialize the SlideshowView and data
     */
    private void init(Context context) {

        // Setup Layout
        LayoutInflater inflater = LayoutInflater.from(context);
        RelativeLayout rootLayout = (RelativeLayout) inflater.inflate(R.layout.slideshow_layout, null);
        vpSlider = (ViewPager) rootLayout.findViewById(R.id.vp_slider);
        tabIndicator = (TabLayout) rootLayout.findViewById(R.id.slide_indicator);
        tvPageNum = (TextView) rootLayout.findViewById(R.id.tv_slide_num);

        setupIndicator(slideshowHandler.isShowingIndicator());
        setupSlideNumber(slideshowHandler.showSlideNumber());
        addView(rootLayout);        // Add layout to the root layout

    }

    /**
     * Update page number TextView
     */
    private void setPageNumber(int position) {
        tvPageNum.setText(position + " / " + slideshowHandler.getSlideList().size());
    }

    /**
     * Setup the tab indicator
     * @param isShowingIndicator boolean value for showing the tab indicator
     */
    private void setupIndicator(boolean isShowingIndicator) {

        if (isShowingIndicator)
            tabIndicator.setVisibility(VISIBLE);            // Show the indicator
        else
            tabIndicator.setVisibility(GONE);               // Does not use the indicator

    }

    /**
     * Setup the slide number view
     * @param isShowingSlideNumber boolean value for showing the slide number
     */
    protected void setupSlideNumber(boolean isShowingSlideNumber) {

        if (isShowingSlideNumber) {
            // Show the slide number
            tvPageNum.setVisibility(VISIBLE);
            vpSlider.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

                @Override
                public void onPageSelected(int index) {
                    super.onPageSelected(index);
                    int position = index + 1;
                    tvPageNum.setText(position + " / " + slideshowHandler.getSlideList().size());
                }
            });
        } else
            tvPageNum.setVisibility(GONE);              // Does not use the slide number

    }

    // =============================================================================================
    // Classes
    // =============================================================================================

    /**
     * The SlideshowTimer class helps update the slide on certain period of time
     */
    private class SlideshowTimer extends TimerTask {

        @Override
        public void run() {

            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    int slideIndex;
                    if (vpSlider.getCurrentItem() < slideshowHandler.getSlideList().size() - 1)
                        slideIndex = vpSlider.getCurrentItem() + 1;
                    else
                        slideIndex = 0;
                    vpSlider.setCurrentItem(slideIndex);

                    if (slideshowHandler.showSlideNumber())
                        setPageNumber(slideIndex + 1);            // Update only when slide is enabled

                }
            });
        }
    }

    // =============================================================================================
    // Accessable Methods
    // =============================================================================================
    public void launch() throws SlideNullPointerException {

        List<ViewGroup> slideList = slideshowHandler.getSlideList();
        if (slideList != null) {

            SlideAdapter slideAdapter = new SlideAdapter(slideList);
            vpSlider.setAdapter(slideAdapter);

            if (slideshowHandler.isShowingIndicator())
                tabIndicator.setupWithViewPager(vpSlider, true);        // Setup indicator only when it is on

            if (slideshowHandler.showSlideNumber())
                setPageNumber(1);       // Update only when slide is enabled

            long periodTime = slideshowHandler.getDelayTimePeriod() * 1000;
            Timer sliderTimer = new Timer();
            sliderTimer.scheduleAtFixedRate(new SlideshowTimer(), 4000, periodTime);

        } else
            throw new SlideNullPointerException(context.getString(R.string.list_null_error));      // Null Exception

    }

    /**
     * Returns the list of ViewGroup slide
     * @return the list of ViewGroup
     */
    public List<ViewGroup> getSlideList() {
        return slideshowHandler.getSlideList();
    }

    /**
     * Set slide list
     * @param slideList List of ViewGroup slide which user created
     * @throws SlideOutOfBoundException on list size is greater than 10
     */
    public void setSlideList(List<ViewGroup> slideList) throws SlideOutOfBoundException {
        slideshowHandler.setSlideList(slideList);
    }

    /**
     * Returns the value of showing tab indicator
     * @return true if showing the tab indicator, otherwise false
     */
    public boolean isShowingIndicator() {
        return slideshowHandler.isShowingIndicator();
    }

    /**
     * Set the value of tab indicator
     * @param isShowingIndicator boolean value for showing the tab indicator
     */
    public void showIndicator(boolean isShowingIndicator) {
        setupIndicator(isShowingIndicator);
        slideshowHandler.showIndicator(isShowingIndicator);
    }

    /**
     * Returns the value of slide delay time period
     * @return slide delay time in second
     */
    public long getDelayTimePeriod() {
        return slideshowHandler.getDelayTimePeriod();
    }

    /**
     * Set the value of slide delay time period
     * @param delayTimePeriod slide delay time in second
     * @exception IllegalArgumentException on delay time is set less than 0 or greater than 10
     */
    public void setDelayTimePeriod(int delayTimePeriod) throws IllegalArgumentException {
        slideshowHandler.setDelayTimePeriod(delayTimePeriod);
    }

    /**
     * Returns the value of showing slide number
     * @return true if showing the slide number, otherwise false
     */
    public boolean isShowingSlideNumber() {
        return slideshowHandler.showSlideNumber();
    }

    /**
     * Set the value of slide number
     * @param isShowingSlideNumber boolean value for showing the slide number
     */
    public void showSlideNumber(boolean isShowingSlideNumber) {
        setupSlideNumber(isShowingSlideNumber);
        slideshowHandler.setShowingSlideNumber(isShowingSlideNumber);
    }

}

