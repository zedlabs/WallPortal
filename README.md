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
[**Download**](https://github.com/zedlabs/WallPortal/releases/download/2020.2/v1.5.1.apk)

native android wallpaper application written in kotlin using jetpack compose

<img src="https://github.com/zedlabs/WallPortal/blob/master/screenshots/sc0.png" width="200" height="400">    <img src="https://github.com/zedlabs/WallPortal/blob/master/screenshots/sc1.png" width="200" height="400"> <img src="https://github.com/zedlabs/WallPortal/blob/master/screenshots/sc2.png" width="200" height="400"> <img src="https://github.com/zedlabs/WallPortal/blob/master/screenshots/sc3.png" width="200" height="400"> 

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
    * Tests - local and instrumented unit tests 
    * [PastelPlaceholders](https://github.com/zedlabs/pastelPlaceholders) - internal image placeholder library
* Architecture
    * Model-View-ViewModel
    * [Android Architecture components](https://developer.android.com/topic/libraries/architecture) ([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [LiveData](https://developer.android.com/topic/libraries/architecture/livedata), [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation))
 
 * Todo
   * create more test cases
   * UI refactor in compose ✔
