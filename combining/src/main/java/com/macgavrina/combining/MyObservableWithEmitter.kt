package com.macgavrina.combining

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class MyObservableWithEmitter {

    fun createObservableWithEmitterA(): Observable<String>? {
        val myObservable = Observable.create<String> { emitter ->
            emitter.onNext("A: First item")
            TimeUnit.SECONDS.sleep(3)
            emitter.onNext("A: Second item")
            TimeUnit.SECONDS.sleep(3)
            //emitter.onError(Throwable("Sample error"));
            emitter.onNext("A: Third item")
            TimeUnit.SECONDS.sleep(3)
            emitter.onComplete()
        }

        return myObservable
    }

    fun createObservableWithEmitterB(): Observable<String>? {
        val myObservable = Observable.create<String> { emitter ->
            emitter.onNext("B: First item")
            TimeUnit.SECONDS.sleep(4)
            emitter.onNext("B: Second item")
            TimeUnit.SECONDS.sleep(4)
            //emitter.onError(Throwable("Sample error"));
            emitter.onComplete()
        }

        return myObservable
    }
}