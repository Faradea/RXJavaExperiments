package com.macgavrina.rxjavaexperiments

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class JustObservable {

    fun letsGo() {
        val observable = createObservableWithJust()
        //subscribeToEvents(observable)
        subscribeWithToEvents(observable)
    }

    private fun createObservableWithJust(): Observable<String> {
        Log.d("AppLogs", "generating some event...")

        //Emits 0 or n items and terminates with an success or an error event.
        val observable: Observable<String> = Observable.just("Hello")

        return observable
    }

    private fun subscribeWithToEvents(observable: Observable<String>) {
        var disposableObserver = observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : DisposableObserver<String>() {

                override fun onNext(t: String) {
                    Log.d("AppLogs", "onNext, t = $t")
                }

                override fun onError(e: Throwable) {}

                override fun onComplete() {
                    Log.d("AppLogs", "onComplete")
                    dispose()
                }
            })
    }

    //Legacy from rxJava 1.1
    private fun subscribeToEvents(observable: Observable<String>) {

        val disposable = observable.subscribe ({ it ->
            Log.d("AppLogs", "it = $it")
        }, { throwableError ->
            Log.d("AppLogs", "Error = $throwableError")
        })
    }
}