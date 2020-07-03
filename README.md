# Android Slider Views Library

Android Slider Views Library supports user to create <b>horizontal and vertical CarouselViews</b> and <b>SlideshowView</b> on Android application.


## Sample GIF Images

<center>
  <table>
    <tr style="border-collapse: collapse;">
      <td><img src="gifs/horizontal_carousel.gif" width="250" /></td>
      <td><img src="gifs/vertical_carousel.gif" width="250" /></td>
    </tr>
    <tr>
      <td>Horizontal Carousel</td>
      <td>Vertical Carousel</td>
    </tr>
   </table>
 </center>
 
## How To Use Slider Views

<b>1. Setup your Android project setting</b>

Minimum SDK Version: 21 or greater (Update in your <i>app level</i> `build.gradle`)
<br/><br/>
<b>2. Add required library</b>

First, include following jitpack url inside maven block in your <i>project level</i> `build.gradle`.
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Next, add the SliderViews library in <i>app level</i> `build.gradle` and sync the gradle file. 
```gradle
implementation 'com.github.sung2063:AndroidSliderViewsLibrary:1.1'
```

Now you are ready to use SliderView Library. You can start creating CarouselView and SlideshowView.
<br/><br/>
### CarouselView

CarouselView can be used for your application intro and show multiple images or videos in one layout with scrolling. CarouselView by Sliders library supports both horizontal and vertical scrolls.

First, create a CarouselView in your xml file.
```xml
<com.sung2063.sliders.carousel.CarouselView
        android:id="@+id/carousel_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        custom:scrollDirection="horizontal"
        custom:showSlideNumber="true" />
```
Set `scrollDirection` attribution to `horizontal` for creating horizontal carousel and `vertical` for vertical carousel.

Next, in your onCreate() method in Activity, create your own custom layouts, create List, and start the carousel.
```java
CarouselView carouselView = findViewById(R.id.carousel_view);

// Create your own layout and add layouts to List<ViewGroup>...

carouselView.setSlideList(verticalCarouselLayouts);
carouselView.launch();
```
<br/><br/>
### SlideshowView

SlideshowView can be used to show the multiple layouts by certain period of time. You can set how much time you want to show each layout to the user.

First, create a SlideshowView in your xml file.
```xml
<com.sung2063.sliders.slideshow.SlideshowView
        android:id="@+id/slideshow_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        custom:showIndicator="true"
        custom:delayTimePeriod="5"
        custom:showSlideNumber="true" />
```

In your onCreate() method in Activity, create the custom layouts and start the slideshow.

```java
SlideshowView slideshowView = findViewById(R.id.slideshow_view);

// Create your own layout and add layouts to List<ViewGroup>...

slideshowView.setSlideList(slideshowLayouts);
slideshowView.launch();
```

