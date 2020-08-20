# WallPortal
[![Kotlin Version](https://img.shields.io/badge/Kotlin-1.3.41-blue.svg)](https://kotlinlang.org)
###### *Minimal Wallpapers for Android*

the goal of this project is to create a responsive application and with optimized networking, a good place to see implementation.
moved to wallhaven.cc api from unsplashed api due to rate limits.

<img src="https://github.com/zedlabs/WallPortal/blob/master/main_page.png" width="200" height="400"> <img src="https://github.com/zedlabs/WallPortal/blob/master/with_slider_open.png" width="200" height="400"> <img src="https://github.com/zedlabs/WallPortal/blob/master/inside_activity.png" width="200" height="400"> 

* Tech-stack
    * [Kotlin](https://kotlinlang.org/) + [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operation
    * [Retrofit](https://square.github.io/retrofit/) - networking
    * [Jetpack](https://developer.android.com/jetpack)
        * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) - deal with whole in-app navigation
        * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - notify views about database change
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform action when lifecycle state changes
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way
        * [Paging](https://developer.android.com/jetpack/androidx/releases/paging)
* Architecture
    * Clean Architecture
    * MVVM
    * [Android Architecture components](https://developer.android.com/topic/libraries/architecture) ([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [LiveData](https://developer.android.com/topic/libraries/architecture/livedata), [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation))
 
 ### Contributing
 * contributions are welcome for animations, UI fixes
 * create issues for feature requests
 * new featues and livraries only in beta branch.
 
  ### ToDo
   * dagger
   * better architecture
 
Code Review by GDE Filip Babic ( 40:00 ) - https://www.raywenderlich.com/10527046-review-my-stuff/lessons/3
