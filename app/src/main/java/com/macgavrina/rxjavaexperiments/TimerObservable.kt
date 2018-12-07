package com.macgavrina.rxjavaexperiments

import android.util.Log
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class TimerObservable {

    fun createTimerObservable(): Observable<Long>? {
        Log.d("AppLogs", "generating some event...")

        //Emits 0 or n items and terminates with an success or an error event.
        val observable = Observable.timer(5, TimeUnit.SECONDS)

        return observable
    }
}