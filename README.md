# vigor
*Witness the fitness! - Roots Manuva*

![Circle CI build status](https://circleci.com/gh/vaughandroid/vigor.svg?style=shield&circle-token=e50d61fd1a517f18fab4ea19f2813726e6f4752a)

## About

An abandoned toy project, this is the beginnings of an app for recording & tracking workouts.

Right now, the app consists of:
1. A 'workout' screen, which is a list of exercises. 
2. An 'exercise' screen, which lets you add/edit an exercise.
3. An 'exercise type' screen, which lets you select from a few different exercise types. 

It was primarily intended as an investigation into Fernando Cejas' 'Android clean architecture' approach (see [here](http://fernandocejas.com/2014/09/03/architecting-android-the-clean-way/) and [here](http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/)). In that regard, I think it was successful. The separation into app, domain and data layers and use of things like UseCases, Repositories, Mappers & DTOs (Data Transfer Objects) is overkill for such a small project, but by improving my understanding of their usage I proved to myself their benefits and viability for larger projects.  

I was also playing around with RxJava and Firebase database when I wrote this. Probably a case of biting off more than I could chew!

Things that I'm proud of/are worth looking at:
* The architecture. As I mentioned earlier, it's overkill for a project of this size, but pays dividends for larger projects.
* The test Robot approach used in the instrumented tests (inspired by [this presentation](https://realm.io/news/kau-jake-wharton-testing-robots/) by Jake Wharton).
* The structure of the acceptance tests - e.g. using [TestApplication](https://github.com/vaughandroid/vigor/blob/dev/app/src/androidTest/java/vaughandroid/vigor/TestApplication.java) and [TestApplicationTestRunner](https://github.com/vaughandroid/vigor/blob/dev/app/src/androidTest/java/vaughandroid/vigor/TestApplicationTestRunner.java) to inject Mocks & Stubs using Dagger 2.
 
Things I'm less proud of:
* `@Nullable` and `@NonNull` are a bit overused. I really like the extra safety, but I think it hurts readability. (Kotlin's solution to this is much, much better.)
* Use of `Observable.create()` in a couple of places. I'm still on the learning curve with RxJava, but know enough to know that this is generally best avoided.
* The comments are quite sparse, as I didn't originally intend to share this.

Regarding tests, this was originally without any meaningful tests since this was primarily a prototype/investigation piece, as I've mentioned earlier. I've added a few to try and give an flavour of how I approach testing, and cover a good selection of classes. 

## Useful commands

* `./gradlew :app:test` - Run all unit tests.
* `./gradlew :app:connectedAndroidTest` - Run instrumented tests (acceptance tests).  
