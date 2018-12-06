package com.macgavrina.rxjavaexperiments

import android.util.Log
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableHelper.dispose
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SingleObservableWithStringResult {

    fun letsGo() {
        val observable = createObservableFromActionWithResult()
        //subscribeToEvents(observable)
        subscribeWithToEvents(observable)
    }

    fun createObservableFromActionWithResult(): Single<String> {
        Log.d("AppLogs", "generating some event...")

        //Emits 0 or n items and terminates with an success or an error event.
        val observable: Single<String> = Single.fromCallable {
            someSyncFunc()
        }
        return observable
    }

    private fun someSyncFunc(): String {
        Log.d("AppLogs","start job")
        TimeUnit.SECONDS.sleep(5)
        Log.d("AppLogs", "finish job")
        return "I'm ready now!"
    }

    private fun subscribeWithToEvents(observable: Single<String>) {
        var disposableObserver = observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : SingleObserver<String> {

                override fun onSubscribe(d: Disposable) {
                }

                override fun onSuccess(t: String) {
                    Log.d("AppLogs", "onSuccess, t = $t")
                }

                override fun onError(e: Throwable) {}

            })
    }
}