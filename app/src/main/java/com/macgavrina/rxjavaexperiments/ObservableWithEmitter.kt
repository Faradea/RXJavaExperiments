package com.macgavrina.rxjavaexperiments

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class ObservableWithEmitter {

    fun createObservableWithEmitter(): Observable<String>? {
        val myObservable = Observable.create<String> { emitter ->
            emitter.onNext("First item")
            emitter.onNext("Second item")
            //emitter.onError(Throwable("Sample error"));
            emitter.onNext("Third item")
            emitter.onComplete()
        }

        return myObservable
    }
}