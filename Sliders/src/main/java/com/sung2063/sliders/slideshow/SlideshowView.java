package com.sung2063.sliders.slideshow;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
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
import com.sung2063.sliders.exception.IllegalArgumentException;
import com.sung2063.sliders.exception.SlideNullPointerException;
import com.sung2063.sliders.exception.SlideOutOfBoundException;
import com.sung2063.sliders.model.DescriptiveSlideModel;
import com.sung2063.sliders.util.UnitConverter;

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
    private TextView tvPageNum, tvSubTitle;

    // Data Objects
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
            float indicatorScale = typedArray.getFloat(R.styleable.SlideshowView_indicatorScale, 1);
            Drawable indicatorSelectedIcon = typedArray.getDrawable(R.styleable.SlideshowView_indicatorSelectedIcon);
            Drawable indicatorUnselectedIcon = typedArray.getDrawable(R.styleable.SlideshowView_indicatorUnselectedIcon);
            boolean isShowingSlideNumber = typedArray.getBoolean(R.styleable.SlideshowView_showSlideNumber, false);
            int slideNumberTextSize = typedArray.getInt(R.styleable.SlideshowView_slideNumberTextSize, 45);
            int delayTimePeriod = typedArray.getInt(R.styleable.SlideshowView_delayTimePeriod, 5);
            boolean isShowingSubTitle = typedArray.getBoolean(R.styleable.SlideshowView_showSubTitle, false);

            // Default value check
            if (indicatorSelectedIcon == null) {
                indicatorSelectedIcon = context.getDrawable(R.drawable.circle_indicator_selected);
            }

            if (indicatorUnselectedIcon == null) {
                indicatorUnselectedIcon = context.getDrawable(R.drawable.circle_indicator_default);
            }

            // Check illegal exception
            if (indicatorScale < 0.5 || indicatorScale > 1.5) {
                throw new IllegalArgumentException(context.getString(R.string.indicator_scale_illegal_error));
            }

            if (slideNumberTextSize < 20 || slideNumberTextSize > 50) {
                throw new IllegalArgumentException(context.getString(R.string.slide_number_text_size_illegal_error));
            }

            if (delayTimePeriod < 0 || delayTimePeriod > 10) {
                throw new IllegalArgumentException(context.getString(R.string.slide_delay_time_illegal_error));
            }

            slideshowHandler = new SlideshowHandler(context, isShowingIndicator, indicatorScale, indicatorSelectedIcon, indicatorUnselectedIcon, isShowingSlideNumber, slideNumberTextSize, delayTimePeriod, isShowingSubTitle);

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
        vpSlider = rootLayout.findViewById(R.id.vp_slider);
        tabIndicator = rootLayout.findViewById(R.id.slide_indicator);
        tvPageNum = rootLayout.findViewById(R.id.tv_slide_num);
        tvSubTitle = rootLayout.findViewById(R.id.tv_sub_title);

        setupIndicator(slideshowHandler.isShowingIndicator(), slideshowHandler.getIndicatorScale());
        setupSlideNumber(slideshowHandler.showSlideNumber(), slideshowHandler.getSlideNumberTextSize());
        addView(rootLayout);        // Add layout to the root layout

    }

    /**
     * Update page number TextView
     */
    private void setPageNumber(int position) {
        List<ViewGroup> slideList = slideshowHandler.getSlideList();
        if (slideList != null) {
            tvPageNum.setText(position + " / " + slideList.size());
        } else {
            tvPageNum.setText(position + " / " + slideshowHandler.getDescriptiveSlideList().size());
        }
    }

    /**
     * Setup the tab indicator
     * @param isShowingIndicator boolean value for showing the tab indicator
     * @param indicatorScale float value for indicator scale
     */
    private void setupIndicator(boolean isShowingIndicator, float indicatorScale) {
        if (isShowingIndicator) {
            tabIndicator.setVisibility(VISIBLE);            // Show the indicator
            tabIndicator.setScaleX(indicatorScale);
            tabIndicator.setScaleY(indicatorScale);

            // Setup Tab Indicator Listener
            tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int currentPosition = tab.getPosition();
                    for (int i = 0; i < tabIndicator.getTabCount(); i++) {
                        tabIndicator.getTabAt(i).setIcon(slideshowHandler.getIndicatorUnselectedIcon());
                    }
                    tabIndicator.getTabAt(currentPosition).setIcon(slideshowHandler.getIndicatorSelectedIcon());
                    subTitleOperation(currentPosition);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

        } else {
            tabIndicator.setVisibility(GONE);               // Does not use the indicator
        }
    }

    /**
     * Setup the slide number view
     * @param isShowingSlideNumber boolean value for showing the slide number
     * @param slideNumberTextSize text size of slide number
     */
    protected void setupSlideNumber(boolean isShowingSlideNumber, int slideNumberTextSize) {

        if (isShowingSlideNumber) {
            // Show the slide number
            tvPageNum.setVisibility(VISIBLE);

            // Convert text size in sp
            float slideNumberTextSizeInDP = UnitConverter.convertTextPXToSP(context, slideNumberTextSize);
            tvPageNum.setTextSize(slideNumberTextSizeInDP);

            vpSlider.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int index) {
                    super.onPageSelected(index);
                    int position = index + 1;

                    List<ViewGroup> slideList = slideshowHandler.getSlideList();
                    if (slideList != null) {
                        tvPageNum.setText(position + " / " + slideList.size());
                    } else {
                        tvPageNum.setText(position + " / " + slideshowHandler.getDescriptiveSlideList().size());
                    }
                }
            });
        } else {
            tvPageNum.setVisibility(GONE);              // Does not use the slide number
        }

    }

    /**
     * Operate subtitle visibility
     *
     * @param position current tab position
     */
    public void subTitleOperation(int position) {
        List<DescriptiveSlideModel> descriptiveSlideList = slideshowHandler.getDescriptiveSlideList();
        if (descriptiveSlideList != null && slideshowHandler.isShowingSubTitle()
                && (descriptiveSlideList.get(position).getSubTitle() != null && !TextUtils.isEmpty(descriptiveSlideList.get(position).getSubTitle()))) {
            tvSubTitle.setText(descriptiveSlideList.get(position).getSubTitle());
            tvSubTitle.setVisibility(VISIBLE);
        } else {
            tvSubTitle.setVisibility(GONE);
        }
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

            ((Activity) context).runOnUiThread(() -> {
                int slideIndex;

                List<ViewGroup> slideList = slideshowHandler.getSlideList();
                List<DescriptiveSlideModel> descriptiveSlideList = slideshowHandler.getDescriptiveSlideList();

                int sliderSize;
                if (slideList != null) {
                    sliderSize = slideList.size();
                } else {
                    sliderSize = descriptiveSlideList.size();
                }

                if (vpSlider.getCurrentItem() < sliderSize - 1) {
                    slideIndex = vpSlider.getCurrentItem() + 1;
                } else {
                    slideIndex = 0;
                }
                vpSlider.setCurrentItem(slideIndex);

                if (slideshowHandler.showSlideNumber()) {
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
        List<DescriptiveSlideModel> descriptiveSlideList = slideshowHandler.getDescriptiveSlideList();

        if (slideList == null && descriptiveSlideList == null) {
            throw new SlideNullPointerException(context.getString(R.string.list_null_error));      // Null Exception
        }

        SlideAdapter slideAdapter = new SlideAdapter(slideList, descriptiveSlideList);
        vpSlider.setAdapter(slideAdapter);

        if (slideshowHandler.isShowingIndicator()) {
            tabIndicator.setupWithViewPager(vpSlider, true);        // Setup indicator only when it is on
        }

        if (slideshowHandler.showSlideNumber()) {
            setPageNumber(1);       // Update only when slide is enabled
        }

        long periodTime = slideshowHandler.getDelayTimePeriod() * 1000;
        Timer sliderTimer = new Timer();
        sliderTimer.scheduleAtFixedRate(new SlideshowTimer(), 4000, periodTime);

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
     * Set descriptive slide list
     *
     * @param slideList List of ViewGroup slide & subTitle which user created
     * @throws SlideOutOfBoundException on list size is greater than 10
     */
    public void setDescriptiveSlideList(List<DescriptiveSlideModel> slideList) throws SlideOutOfBoundException {
        slideshowHandler.setDescriptiveSlideList(slideList);
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
        setupIndicator(isShowingIndicator, slideshowHandler.getIndicatorScale());
        slideshowHandler.showIndicator(isShowingIndicator);
    }

    /**
     * Set indicator scale
     * @param indicatorScale float value for indicator scale
     */
    public void setIndicatorScale(int indicatorScale) {
        setupIndicator(slideshowHandler.isShowingIndicator(), indicatorScale);
        slideshowHandler.setIndicatorScale(indicatorScale);
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
        setupSlideNumber(isShowingSlideNumber, slideshowHandler.getSlideNumberTextSize());
        slideshowHandler.setShowingSlideNumber(isShowingSlideNumber);
    }

    /**
     * Set the slide number text size
     * @param slideNumberTextSize int value for slide number text size
     */
    public void setSlideNumberTextSize(int slideNumberTextSize) {
        setupSlideNumber(slideshowHandler.showSlideNumber(), slideNumberTextSize);
        slideshowHandler.setSlideNumberTextSize(slideNumberTextSize);
    }

    /**
     * Set subtitle visibility
     */
    public void showSubTitle(boolean isShowingSubTitle) {
        slideshowHandler.setShowingSubTitle(isShowingSubTitle);
    }
}

