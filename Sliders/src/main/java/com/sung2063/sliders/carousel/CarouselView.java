package com.sung2063.sliders.carousel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.sung2063.sliders.util.UnitConverter;

import java.util.List;

/**
 * The CarouselView is the view which user can create the carousel with custom layouts either horizontally or vertically.
 *
 * @author Sung Hyun Back
 * @version 1.0
 * @since 2020-07-02
 */
public class CarouselView extends LinearLayout {

    // =============================================================================================
    // Variables
    // =============================================================================================
    private Context context;
    private RelativeLayout rootLayout;
    private ViewPager vpSlider;
    private TabLayout tabIndicator;
    private TextView tvPageNum;
    private ProgressBar pbLayoutLoader;

    // Data Objects
    private final int VERTICAL_LEFT_MARGIN_SPACE = 100;
    private CarouselHandler carouselHandler;

    // =============================================================================================
    // Constructors
    // =============================================================================================
    // Create object by programmatically
    public CarouselView(Context context) {
        super(context);
        this.context = context;
        carouselHandler = new CarouselHandler();
        init();
    }

    // Create object from XML file
    public CarouselView(Context context, @Nullable AttributeSet attrs) throws IllegalArgumentException {
        super(context, attrs);

        this.context = context;
        carouselHandler = new CarouselHandler();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CarouselView, 0, 0);
        try {
            int scrollDirection = typedArray.getInt(R.styleable.CarouselView_scrollDirection, CarouselHandler.CAROUSEL_HORIZONTAL_DIRECTION);
            boolean isShowingIndicator = typedArray.getBoolean(R.styleable.CarouselView_showIndicator, false);
            float indicatorScale = typedArray.getFloat(R.styleable.CarouselView_indicatorScale, 1);
            Drawable indicatorSelectedIcon = typedArray.getDrawable(R.styleable.CarouselView_indicatorSelectedIcon);
            Drawable indicatorUnselectedIcon = typedArray.getDrawable(R.styleable.CarouselView_indicatorUnselectedIcon);
            boolean isShowingSlideNumber = typedArray.getBoolean(R.styleable.CarouselView_showSlideNumber, false);
            int slideNumberTextSize = typedArray.getInt(R.styleable.CarouselView_slideNumberTextSize, 45);

            // Default value check
            if (indicatorSelectedIcon == null) {
                indicatorSelectedIcon = context.getDrawable(R.drawable.circle_indicator_selected);
            }

            if (indicatorUnselectedIcon == null) {
                indicatorUnselectedIcon = context.getDrawable(R.drawable.circle_indicator_default);
            }

            // Check illegal exception
            if (indicatorScale < 0 || indicatorScale > 1.5) {
                throw new IllegalArgumentException(context.getString(R.string.indicator_scale_illegal_error));
            }

            if (slideNumberTextSize < 0 || slideNumberTextSize > 50) {
                throw new IllegalArgumentException(context.getString(R.string.slide_number_text_size_illegal_error));
            }

            carouselHandler = new CarouselHandler(context, scrollDirection, isShowingIndicator, indicatorScale, indicatorSelectedIcon, indicatorUnselectedIcon, isShowingSlideNumber, slideNumberTextSize);

        } finally {
            typedArray.recycle();
            init();
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
     * Initialize the CarouselView and data
     */
    protected void init() {

        // Setup Layout
        setupLayoutDirection(carouselHandler.getScrollDirection());
        setupIndicator(carouselHandler.isShowingIndicator(), carouselHandler.getIndicatorScale());
        setupSlideNumber(carouselHandler.isShowingSlideNumber(), carouselHandler.getSlideNumberTextSize());

    }

    /**
     * Update page number TextView
     */
    private void setPageNumber(int position) {
        tvPageNum.setText(position + " / " + carouselHandler.getSlideList().size());
    }

    /**
     * Repositioning the tab when the direction is vertical
     */
    private void repositioningVerticalTab() {
        // Get length of tab layout
        RelativeLayout.LayoutParams tabLayoutParams = (RelativeLayout.LayoutParams) tabIndicator.getLayoutParams();
        int viewLength = tabIndicator.getRight() - tabIndicator.getLeft();
        int centerPosition = (int) (viewLength / 2) - VERTICAL_LEFT_MARGIN_SPACE;
        int marginLeft = (-1) * centerPosition;
        tabLayoutParams.setMargins(marginLeft, 0, 0, 0);
        tabIndicator.setLayoutParams(tabLayoutParams);
    }

    /**
     * Setup the layout direction
     * @param layoutDirection value for layout direction in integer
     */
    private void setupLayoutDirection(int layoutDirection) {

        if (rootLayout != null) {
            removeView(rootLayout);         // Remove layout first
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        if (layoutDirection == CarouselHandler.CAROUSEL_HORIZONTAL_DIRECTION) {
            // Scroll horizontally
            rootLayout = (RelativeLayout) inflater.inflate(R.layout.horizontal_carousel_layout, null);
            vpSlider = (ViewPager) rootLayout.findViewById(R.id.vp_slider);
            tabIndicator = (TabLayout) rootLayout.findViewById(R.id.slide_indicator);
            tvPageNum = (TextView) rootLayout.findViewById(R.id.tv_slide_num);
            pbLayoutLoader = (ProgressBar) rootLayout.findViewById(R.id.pb_layout_loader);
        } else {
            // Scroll vertically
            rootLayout = (RelativeLayout) inflater.inflate(R.layout.vertical_carousel_layout, null);
            vpSlider = (ViewPager) rootLayout.findViewById(R.id.vp_slider);
            tabIndicator = (TabLayout) rootLayout.findViewById(R.id.slide_indicator);
            tvPageNum = (TextView) rootLayout.findViewById(R.id.tv_slide_num);
            pbLayoutLoader = (ProgressBar) rootLayout.findViewById(R.id.pb_layout_loader);
        }
        addView(rootLayout);        // Add layout to the root layout

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
                    for (int i = 0; i < tabIndicator.getTabCount(); i++) {
                        tabIndicator.getTabAt(i).setIcon(carouselHandler.getIndicatorUnselectedIcon());
                    }
                    tabIndicator.getTabAt(tab.getPosition()).setIcon(carouselHandler.getIndicatorSelectedIcon());
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
    private void setupSlideNumber(boolean isShowingSlideNumber, int slideNumberTextSize) {

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
                    tvPageNum.setText(position + " / " + carouselHandler.getSlideList().size());
                }
            });

        } else {
            tvPageNum.setVisibility(GONE);      // Does not use the slide number
        }

    }

    // =============================================================================================
    // Accessible Methods
    // =============================================================================================
    public void launch() throws SlideNullPointerException {

        List<ViewGroup> slideList = carouselHandler.getSlideList();
        if (slideList != null) {

            // Connect slideAdapter to ViewPager and indicator
            SlideAdapter slideAdapter = new SlideAdapter(slideList);
            vpSlider.setAdapter(slideAdapter);
            tabIndicator.setupWithViewPager(vpSlider, true);
            setPageNumber(1);           // Initial Page

            // Give sometime for loading
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                repositioningVerticalTab();
                pbLayoutLoader.setVisibility(GONE);
            }, 250);

        } else {
            throw new SlideNullPointerException(context.getString(R.string.list_null_error));      // Null Exception
        }

    }

    /**
     * Returns the list of ViewGroup slide
     * @return the list of ViewGroup
     */
    public List<ViewGroup> getSlideList() {
        return carouselHandler.getSlideList();
    }

    /**
     * Set slide list
     * @param slideList List of ViewGroup slide which user created
     * @throws SlideOutOfBoundException on list size is greater than 10
     */
    public void setSlideList(List<ViewGroup> slideList) throws SlideOutOfBoundException {
        carouselHandler.setSlideList(slideList);
    }

    /**
     * Returns the scroll direction
     * @return 0 for horizontal direction or 1 for vertical direction
     */
    public int getScrollDirection() {
        return carouselHandler.getScrollDirection();
    }

    /**
     * Set scroll direction
     * @param scrollDirection scroll direction either horizontal or vertical in integer
     */
    public void setScrollDirection(int scrollDirection) {
        setupLayoutDirection(scrollDirection);
        carouselHandler.setScrollDirection(scrollDirection);
    }

    /**
     * Returns the value of showing tab indicator
     * @return true if showing the tab indicator, otherwise false
     */
    public boolean isShowingIndicator() {
        return carouselHandler.isShowingIndicator();
    }

    /**
     * Set the value of tab indicator
     * @param isShowingIndicator boolean value for showing the tab indicator
     */
    public void showIndicator(boolean isShowingIndicator) {
        setupIndicator(isShowingIndicator, carouselHandler.getIndicatorScale());
        carouselHandler.showIndicator(isShowingIndicator);
    }

    /**
     * Returns the value of showing slide number
     * @return true if showing the slide number, otherwise false
     */
    public boolean isShowingSlideNumber() {
        return carouselHandler.isShowingSlideNumber();
    }

    /**
     * Set indicator scale
     * @param indicatorScale float value for indicator scale
     */
    public void setIndicatorScale(float indicatorScale) {
        setupIndicator(carouselHandler.isShowingIndicator(), indicatorScale);
        carouselHandler.setIndicatorScale(indicatorScale);
    }

    /**
     * Set the value of slide number
     * @param isShowingSlideNumber boolean value for showing the slide number
     */
    public void showSlideNumber(boolean isShowingSlideNumber) {
        setupSlideNumber(isShowingSlideNumber, carouselHandler.getSlideNumberTextSize());
        carouselHandler.showSlideNumber(isShowingSlideNumber);
    }

    /**
     * Set the slide number text size
     * @param slideNumberTextSize int value for slide number text size in px
     */
    public void setSlideNumberTextSize(int slideNumberTextSize) {
        setupSlideNumber(carouselHandler.isShowingSlideNumber(), slideNumberTextSize);
        carouselHandler.setSlideNumberTextSize(slideNumberTextSize);
    }

}
