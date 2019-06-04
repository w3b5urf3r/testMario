# Rokt Test

## Android test
This project is  based on my own architecture, same architecture used as framework in my company, I wrote every part of it. 

This test project is for an expert audience

The project highly relies on:
  - [Android Architecture components](https://developer.android.com/topic/libraries/architecture/guide.html)
   Of which I recommend the reading if you haven't already.
  
it's totally based on androidX and KTX. enterily written in Kotlin. 
I use couroutines for asynchronous jobs and RXBinding for UI (easier and more compact)
 
###  Test challenges comments:

I had to find a balance between quality and achievement of the required objectives. 
The idea is to build each componenent in indipendent libraries
e.g: network for API operations
persistence for the DB layer (adding a number)
after this moving the dependency for the two module to `libraryTest` which the library module it self
this last point is not yet covered for lack of time, but the separation of concerns is actually visible from the current structure. 


  

## Libraries
  - [Kotlin](https://kotlinlang.org/)
  - [Anko commons](https://github.com/Kotlin/anko/wiki) (logging,dialogs,intents etc.. )
  - [Data binding](https://developer.android.com/topic/libraries/data-binding/index.html)
  - [KodeIn for DI](https://github.com/SalomonBrys/Kodein) 
  - [Retrofit](http://square.github.io/retrofit/)
  -  Rx
  -  Coroutines
  - [KTX](https://github.com/android/android-ktx)

## Utilities
  - [AuthManager] Utility to handle expiring tokens
  - [BindUtils] common convienience method for binding
 
## Rx features
  - [Rx Android](https://github.com/ReactiveX/RxAndroid)
  - [Rx Java](https://github.com/ReactiveX/RxJava)
  - [Rx Kotlin](https://github.com/ReactiveX/RxKotlin) 
  - [Rx Bindings] optional see commented code in build.gradle
