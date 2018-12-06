package com.macgavrina.rxjavaexperiments

import android.util.Log
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CompletableObservable {

    fun letsGo() {
        val completable:Completable = createCompletable()
        subscribeWithToEvents(completable)
    }

    private fun createCompletable(): Completable {

        Log.d("AppLogs", "generating some event...")

        val completable: Completable = Completable.fromAction {
            Log.d("AppLogs","start job")
            TimeUnit.SECONDS.sleep(5)
            Log.d("AppLogs", "finish job")
        }

        return completable
    }

    private fun subscribeWithToEvents(completable: Completable) {
        var completableObserver = completable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {

                override fun onSubscribe(d: Disposable) {}

                override fun onError(e: Throwable) {
                    Log.d("AppLogs", "error = $e")
                }

                override fun onComplete() {
                    Log.d("AppLogs","onComplete")
                    //ToDo dispose
                }
            })
    }

}