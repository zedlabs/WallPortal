# WallPortal
 <a title="GitHub Stars" target="_blank" href="https://github.com/zedlabs/WallPortal/stargazers">
  <img alt="GitHub Stars" src="https://img.shields.io/github/stars/zedlabs/Wallportal.svg?label=Stars&style=social">
  </a>  
  
  <a title="GitHub Forks" target="_blank" href="https://github.com/zedlabs/WallPortal/network/members">
  <img alt="GitHub Forks" src="https://img.shields.io/github/forks/zedlabs/WallPortal.svg?label=Forks&style=social">
  </a>
  </br>
  
 [![Kotlin Version](https://img.shields.io/badge/Kotlin-1.4.10-blue.svg)](https://kotlinlang.org)
###### *Minimal Wallpapers for Android*
**Download from Play Store**

<a href="https://play.google.com/store/apps/details?id=tk.zedlabs.wallaperapp2019" target="_blank">
<img src="https://play.google.com/intl/en_us/badges/images/generic/en-play-badge.png" alt="Get it on Google Play" height="70"/></a>

Native Android wallpaper application written in Kotlin using Jetpack Compose

<img src="https://github.com/zedlabs/WallPortal/blob/master/screenshots/new.png" width="200" height="400">    <img src="https://github.com/zedlabs/WallPortal/blob/master/screenshots/pop.png" width="200" height="400"> <img src="https://github.com/zedlabs/WallPortal/blob/master/screenshots/detCol.png" width="200" height="400"> <img src="https://github.com/zedlabs/WallPortal/blob/master/screenshots/detExp.png" width="200" height="400"> 

* Tech-stack
    * [Kotlin](https://kotlinlang.org/) + [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operation
    * [Retrofit](https://square.github.io/retrofit/) - networking
    * [Hilt](https://github.com/google/dagger) - DI
    * [Jetpack](https://developer.android.com/jetpack)
        * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) - deal with whole in-app navigation
        * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - notify views about database change
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform action when lifecycle state changes
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way
        * [Paging3](https://developer.android.com/jetpack/androidx/releases/paging)
        * [ViewBinding](https://developer.android.com/topic/libraries/view-binding)
        * [Compose]()
    * Tests - local and instrumented unit tests 
* Architecture
    * Model-View-ViewModel
    * [Android Architecture components](https://developer.android.com/topic/libraries/architecture) ([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [LiveData](https://developer.android.com/topic/libraries/architecture/livedata), [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation))


- ``pre-beta`` branch - for implementation without compose 
