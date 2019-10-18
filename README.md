# WallPortal
[![Kotlin Version](https://img.shields.io/badge/Kotlin-1.3.41-blue.svg)](https://kotlinlang.org)
###### *Minimal Wallpapers for Android - 100% Kotlin*

the goal of this project is to provide a clean ad-free android application using the best practices, and in the process create a 
responsive application and more optimized networking, a good place to see implementation

<img src="https://github.com/zedlabs/WallPortal/blob/master/Screenshot_1566835536.png" width="200" height="400"> <img src="https://github.com/zedlabs/WallPortal/blob/master/Screenshot_1566835549.png" width="200" height="400"> <img src="https://github.com/zedlabs/WallPortal/blob/master/Screenshot_1566835576.png" width="200" height="400">

* Tech-stack
    * [Kotlin](https://kotlinlang.org/) + [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operation
    * [Retrofit](https://square.github.io/retrofit/) - networking
    * [Jetpack](https://developer.android.com/jetpack)
        * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/) - deal with whole in-app navigation efficiently
        * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - notify views about database change
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform action when lifecycle state changes
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way
        * [Paging](https://developer.android.com/jetpack/androidx/releases/paging)
* Architecture
    * Clean Architecture
    * MVVM
    * [Android Architecture components](https://developer.android.com/topic/libraries/architecture) ([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), [LiveData](https://developer.android.com/topic/libraries/architecture/livedata), [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation))
    
* Gradle
    * [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)


### In development
 * Implement courotines
 * Implement Jetpack Animations and Slices
