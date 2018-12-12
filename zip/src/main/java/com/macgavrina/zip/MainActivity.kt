package com.macgavrina.zip

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_start.setOnClickListener { _ ->
            startProcessing()
        }
    }

    private fun startProcessing() {

        val single = getDetailsOverall()
        subscribeWithToEvents(single)

    }

    fun getDetailsOverall() : Single<String> {
        return Single.zip(
            getDetailOne(),
            getDetailTwo(),
            mergeEmittedItems())
    }

    private fun mergeEmittedItems(): BiFunction<Int, Int, String> {
        return BiFunction { one, two -> "$one + $two" }
    }

    private fun createStringFromThreeInts(one: Int, two: Int,
                                   three: Int): String {
        return "$one + $two + $three"
    }


    private fun getDetailOne(): Single<Int> {

        return Single.create<Int> { emitter ->
            //emitter.onSuccess(1)
            emitter.onError(Throwable("Sample error"))
        }
//        return Single.fromCallable {
//            produceOneSync()
//        }
    }

    private fun produceOneSync(): Int {
        return 1
    }

    private fun getDetailTwo(): Single<Int> {
        return Single.fromCallable {
            produceTwoSync()
        }
    }

    private fun produceTwoSync(): Int {
        return 2
    }

    private fun getDetailThree(): Single<Int> {
        return Single.fromCallable {
            produceThreeSync()
        }
    }

    private fun produceThreeSync(): Int {
        return 3
    }

    private fun subscribeWithToEvents(observable: Single<String>) {
        var disposableObserver = observable
            .observeOn(AndroidSchedulers.mainThread())

            //subscribeOn() specifies a Scheduler (thread pool) where the work will be performed after subscription is made in subscribe().
            .subscribeOn(Schedulers.io())

            .subscribeWith(object : SingleObserver<String> {

                override fun onSubscribe(d: Disposable) {
                }

                override fun onSuccess(t: String) {
                    Log.d("AppLogs", "onSuccess, t = $t")
                }

                override fun onError(e: Throwable) {
                    Log.d("AppLogs", "error")
                }

            })
    }
}
