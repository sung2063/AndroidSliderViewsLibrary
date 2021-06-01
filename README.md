# Android Slider Views Library

<img src="banner/AndroidSliderViews%20Banner%202.jpg" width="100%" />

<div>
  <img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" style="float:left" />
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" style="float:left" />
</div>
<br/>

[![](https://jitpack.io/v/sung2063/AndroidSliderViewsLibrary.svg)](https://jitpack.io/#sung2063/AndroidSliderViewsLibrary)
[![Generic badge](https://img.shields.io/badge/Build-Passing-13dd1a.svg)](#)
[![Generic badge](https://img.shields.io/badge/Maintained-Yes-13dd1a.svg)](#)
[![Generic badge](https://img.shields.io/badge/License-GNU-13dd1a.svg)](#)

Android Slider Views library supports <b>Carousels</b> and <b>Slideshow</b> Views which developers simply creating a dynamic gallery on the Android application.

## 💖 Sponsor

Android Slider Views library updates regularly. Your valuable sponsorship helps me contributing more features and maintaining the library. Support me for building more interesting projects! 💜

<div align="center">
<a href="https://github.com/sponsors/sung2063"><img src="https://img.shields.io/badge/sponsor-30363D?style=for-the-badge&logo=GitHub-Sponsors&logoColor=#white" style="float: left" /></a>
<a href="https://www.paypal.com/donate?business=sunghyunb1991%40gmail.com&item_name=GitHub+Open+Source+Project+Sponsorship&currency_code=USD"><img src="https://img.shields.io/badge/PayPal-00457C?style=for-the-badge&logo=paypal&logoColor=white" /></a>
</div>

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
 
## 📋 Table of Contents
1. [Latest Update](#update)
2. [Usage Instruction](#instruction)
    1. [Setup Project](#setup_project)
    2. [CarouselView](#carousel_view)
    3. [SlideshowView](#slideshow_view)
3. [Callback Events](#callback_events)
4. [Attributions](#attributions)
    1. [CarouselView](#attr_carousel_view)
    2. [SlideshowView](#attr_slideshow_view)
5. [Contributor](#contributor)
6. [License](#license)

## 🆕 Latest Update <a name="update" />

Updated on <i>May 22nd 2021</i>
- <b>NEW</b>: Slider callback action is now available! ✨

## 📖 How To Use Slider Views <a name="instruction" />

<b>1. Setup your Android project setting</b> <a name="setup_project" />

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
implementation 'com.github.sung2063:AndroidSliderViewsLibrary:1.8'
implementation 'com.google.android.material:material:1.1.0'
```

Now you are ready to use SliderView Library. You can start creating CarouselView and SlideshowView.<br/>

<hr/>

### <i>CarouselView</i> <a name="carousel_view" />

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

<hr/>

### <i>SlideshowView</i> <a name="slideshow_view" />

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

<hr/>

### Callback Events <a name="callback_events" />

You can also get callback events from slider. On your Activity, create a SliderListener object with implementing own action and pass this object to the view by calling `setSliderListener`. Here is the snippet code how to implement the callback: 

```java
// Create a callback interface
SliderListener sliderListener = position -> {
     // TODO: Do something when slide is clicked
};

// Set callback object to the View
carouselView.setSliderListener(sliderListener)    // If you are using Carousel
slideshowView.setSliderListener(sliderListener)   // If you are using Slideshow
```

Check out my <a href="https://github.com/sung2063/AndroidSliderViewSample">Android Slide Sample App</a> project for more example on using SliderView library.

## 🎨 Attributions <a name="attributions" />

Here are available attributions you can use to modify your slider views.

### CarouselView <a name="attr_carousel_view" />

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
      <td>Used for resize the indicator scale from 0.5 - 1.5.</td>
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
    <tr>
      <td>showSlideButtons</td>
      <td>boolean</td>
      <td>Show the slide buttons if the value is true, otherwise do not show. Default is true.</td>
    </tr>
   </table>
 </center>

### SlideshowView <a name="attr_slideshow_view" />

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
      <td>Used for resize the indicator scale from 0.5 - 1.5.</td>
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
    <tr>
      <td>showSlideButtons</td>
      <td>boolean</td>
      <td>Show the slide buttons if the value is true, otherwise do not show. Default is true.</td>
    </tr>
   </table>
 </center>

 ## 🌟 Contributor <a name="contributor" />
 
 Sung Hyun Back (@sung2063)
 
 ## 📝 License <a name="license" />
 
 The code is licensed under the <a href="https://github.com/sung2063/AndroidSliderViewsLibrary/blob/master/LICENSE">GNU General Public License</a>.
