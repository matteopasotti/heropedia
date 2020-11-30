# Heropedia

## Intro
This application is in Kotlin and consumes Marvel Api to retrieve a list of Characters.
The architecture used is MVVM (Model View ViewModel) with a combination of recent Android Architecture Components released By Google.


![mvvm](https://user-images.githubusercontent.com/19550736/47259068-57edf780-d49c-11e8-9190-8fb8ff21b052.png)

If you want to read more about this project and about the components used, i'm writing a series of articles on [Medium](https://medium.com/@matteopasotti)

## How to run the project
The first time you download this repo you will get an error about a file called keys.properties.
The reason of this error is because you should never upload your private keys in Github and that's
why I am storing them into BuildConfig file which will never be uploaded.
So what i recommend you to do is check app/build.gradle file and remove all the lines where i am using BuildConfig,
then read this article (https://guides.codepath.com/android/Storing-Secret-Keys-in-Android)
Get your API KEY from the Marvel's developer portal, handle your own keys and replace

## Screenshots


Splash Screen              |  Home Screen              | Detail Screen             
:-------------------------:|:-------------------------:|:-------------------------:
![screenshot_1540059566](https://user-images.githubusercontent.com/19550736/47259272-f5e2c180-d49e-11e8-9ac6-e25220368147.png)  |  ![screenshot_1540059798](https://user-images.githubusercontent.com/19550736/47259175-ca130c00-d49d-11e8-9c94-2680db3dd1b1.png) |![screenshot_1540059643](https://user-images.githubusercontent.com/19550736/47259265-e6fc0f00-d49e-11e8-86a2-2051194c0286.png)

## Libraries Used

* [Architecture](https://developer.android.com/jetpack/arch/) - A collection of libraries that help you design robust, testable, and maintainable apps. Start with classes for managing your UI component lifecycle and handling data persistence.
  * [DataBinding](https://developer.android.com/topic/libraries/data-binding/) - - Declaratively bind observable data to UI elements.
  * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Build data objects that notify views when the underlying database changes.
  * [Room](https://developer.android.com/topic/libraries/architecture/room) - Access your app's SQLite database with in-app objects and compile-time checks.
  * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - - Store UI-related data that isn't destroyed on app rotations. Easily schedule asynchronous tasks for optimal execution.
  
 ## Upcoming Features
 
 Updates will include incorporating testing parts using Mockito, and new features like the Search of a specific Character
