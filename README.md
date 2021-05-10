# Android Slider Views Library

<img src="banner/AndroidSliderViews%20Banner%202.jpg" width="100%" />

Android Slider Views library supports <b>Carousels</b> and <b>Slideshow</b> Views which developers simply creating a dynamic gallery on the Android application.

[![](https://jitpack.io/v/sung2063/AndroidSliderViewsLibrary.svg)](https://jitpack.io/#sung2063/AndroidSliderViewsLibrary)

## 🎬 Sample GIF Images

<center>
  <table>
    <tr style="border-collapse: collapse;">
      <td><img src="gifs/horizontal_carousel.gif" width="250" /></td>
      <td><img src="gifs/horizontal_carousel%232.gif" width="250" /></td>
      <td><img src="gifs/vertical_carousel.gif" width="250" /></td>
    </tr>
    <tr>
      <td>Horizontal Carousel</td>
      <td>Carousel w/ Custom Indicator</td>
      <td>Vertical Carousel</td>
    </tr>
   </table>
 </center>

## 🆕 Latest Update

Updated on <i>May 10th 2021</i>
- <b>NEW</b>: You now can add sub-title to slide! ✨


## 📖 How To Use Slider Views

<b>1. Setup your Android project setting</b>

Minimum SDK Version: 21 or greater (Update in your <i>app level</i> `build.gradle`)<br/>
Supported Programming Language: Java

Add following snippet code in your <i>project level</i> `build.gradle`.
```gradle
android {
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
```

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

Next, add the SliderViews and required libraries in <i>app level</i> `build.gradle` and sync the gradle file. 
```gradle
implementation 'com.github.sung2063:AndroidSliderViewsLibrary:1.6'
implementation 'com.google.android.material:material:1.1.0'
```

Now you are ready to use SliderView Library. You can start creating CarouselView and SlideshowView.<br/>

### CarouselView

CarouselView can be used for your application intro and show multiple images or videos in one layout with scrolling. CarouselView by Sliders library supports both horizontal and vertical scrolls.

First, create a CarouselView in your xml file.
```xml
<com.sung2063.sliders.carousel.CarouselView
        android:id="@+id/carousel_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:showIndicator="true"
        app:scrollDirection="horizontal"
        app:showSlideNumber="true" />
```
Set `scrollDirection` attribution to `horizontal` for creating horizontal carousel and `vertical` for vertical carousel.

In your `onCreate()` method in Activity, create your own custom layouts, add layouts to List<ViewGroup>, and start the carousel. You can add up to 10 layouts in the CarouselView.

From <i>version 1.6</i>, you can also include sub-title of each slides. In that case, you need to make DescriptiveSlideModel objects that holds slide layout and sub-title and add to ``List<DescriptiveSlideModel>``. Also, do not forget to add <b>app:showSubTitle="true"</b> into your CarouselView in xml. The default is false.

```java
CarouselView carouselView = findViewById(R.id.carousel_view);

// Create your own layouts...
// Create List<ViewGroup> object or List<DescriptiveSlideModel>...
// Add your layouts to list object...

carouselView.setSlideList(layoutList);
carouselView.launch();
```

Your CarouselView is now displayed on your app! 👏<br/>

### SlideshowView

SlideshowView can be used to show the multiple layouts by certain period of time. You can set how much time you want to show each layout to the user.

First, create a SlideshowView in your xml file.
```xml
<com.sung2063.sliders.slideshow.SlideshowView
        android:id="@+id/slideshow_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:showIndicator="true"
        app:delayTimePeriod="5"
        app:showSlideNumber="true" />
```

In your `onCreate()` method in Activity, create your own custom layouts, add layouts to ``List<ViewGroup>``, and start the slideshow. You can add up to 10 layouts in the SlideshowView. 

From <i>version 1.6</i>, you can also include sub-title of each slides. In that case, you need to make DescriptiveSlideModel objects that holds slide layout and sub-title and add to ``List<DescriptiveSlideModel>``. Also, do not forget to add <b>app:showSubTitle="true"</b> into your SlideshowView in xml. The default is false.

```java
SlideshowView slideshowView = findViewById(R.id.slideshow_view);

// Create your own layouts...
// Create List<ViewGroup> object or List<DescriptiveSlideModel>...
// Add your layouts to list object...

slideshowView.setSlideList(layoutList);
slideshowView.launch();
```

Your SlideshowView is now displayed on your app! 👏<br/>

Check out my <a href="https://github.com/sung2063/AndroidSliderViewSample">Android Slide Sample App</a> project for more example on using SliderView library.

## 🎨 Attributions

Here are available attributions you can use to modify your slider views.

### CarouselView

<center>
  <table>
    <tr>
      <th>Attribution</th>
      <th>Value</th>
      <th>Description</th>
    </tr>
    <tr>
      <td rowspan="2">scrollDirection</td>
      <td>horizontal</td>
      <td>Display the carousel horizontally. <i>Field value is 0.</i></td>
    </tr>
    <tr>
      <td>vertical</td>
      <td>Display the carousel vertically. <i>Field value is 1.</i></td>
    </tr>
    <tr>
      <td>showIndicator</td>
      <td>boolean</td>
      <td>Show the dot indicator on the slide if the value true, otherwise do not show.</td>
    </tr>
    <tr>
      <td>indicatorScale</td>
      <td>float</td>
      <td>Used for resize the indicator scale from 0 - 1.5.</td>
    </tr>
    <tr>
      <td>indicatorSelectedIcon</td>
      <td>integer</td>
      <td>Selected indicator icon drawable id.</td>
    </tr>
    <tr>
      <td>indicatorUnselectedIcon</td>
      <td>integer</td>
      <td>Unselected indicator icon drawable id.</td>
    </tr>
    <tr>
      <td>showSlideNumber</td>
      <td>boolean</td>
      <td>Show the slide number text if the value is true, otherwise do not show.</td>
    </tr>
    <tr>
      <td>slideNumberTextSize</td>
      <td>int</td>
      <td>Set the slide number text size in px.</td>
    </tr>
    <tr>
      <td>showSubTitle</td>
      <td>boolean</td>
      <td>Show the sub-title if the value is true, otherwise do not show.</td>
    </tr>
   </table>
 </center>

### SlideshowView

<center>
  <table>
    <tr>
      <th>Attribution</th>
      <th>Value</th>
      <th>Description</th>
    </tr>
    <tr>
      <td>showIndicator</td>
      <td>boolean</td>
      <td>Show the dot indicator on the slide if the value true, otherwise do not show.</td>
    </tr>
    <tr>
      <td>indicatorScale</td>
      <td>float</td>
      <td>Used for resize the indicator scale from 0 - 1.5.</td>
    </tr>
    <tr>
      <td>indicatorSelectedIcon</td>
      <td>integer</td>
      <td>Selected indicator icon drawable id.</td>
    </tr>
    <tr>
      <td>indicatorUnselectedIcon</td>
      <td>integer</td>
      <td>Unselected indicator icon drawable id.</td>
    </tr>
    <tr>
      <td>showSlideNumber</td>
      <td>boolean</td>
      <td>Show the slide number text if the value is true, otherwise do not show.</td>
    </tr>
    <tr>
      <td>slideNumberTextSize</td>
      <td>int</td>
      <td>Set the slide number text size in px.</td>
    </tr>
    <tr>
      <td>delayTimePeriod</td>
      <td>int</td>
      <td>The slide delay time in second. Default is 5 seconds.</td>
    </tr>
    <tr>
      <td>showSubTitle</td>
      <td>boolean</td>
      <td>Show the sub-title if the value is true, otherwise do not show.</td>
    </tr>
   </table>
 </center>

 ## 🌟 Contributor
 
 Sung Hyun Back (@sung2063)

 ## 💖 Sponsorship
 
 Please sponsor me to my GitHub sponsorship page at https://github.com/sponsors/sung2063.
 Your valueable sponsorship helps me contributing more innovative projects and libraries. ❤️
 
 ## 📝 License
 
 The code is licensed under the <a href="https://github.com/sung2063/AndroidSliderViewsLibrary/blob/master/LICENSE">GNU General Public License</a>.
