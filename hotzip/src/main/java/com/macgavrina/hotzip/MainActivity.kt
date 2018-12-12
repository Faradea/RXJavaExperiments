package com.macgavrina.hotzip

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observables.ConnectableObservable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var disposable: Disposable
    lateinit var subscription: Observer<Int>
    lateinit var connectableObservable1: ConnectableObservable<Int>
    lateinit var connectableObservable2: ConnectableObservable<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_hot_observable1.setOnClickListener { _ ->

            val observable = Observable.create<Int> { emitter ->
                emitter.onNext(1)
                //emitter.onError(Throwable("Sample error"))
            }

            connectableObservable1 = observable
                .subscribeOn(Schedulers.io())
                .publish()
        }

        button_hot_observable2.setOnClickListener { _ ->

            val observable = Observable.create<Int> { emitter ->
                emitter.onNext(2)
                //emitter.onError(Throwable("Sample error"))
            }

            connectableObservable2 = observable
                .subscribeOn(Schedulers.io())
                .publish()

        }


        if (::connectableObservable1.isInitialized) {
            Log.d("AppLogs", "connecting...")

            subscription = connectableObservable1
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : Observer<Int> {

                    override fun onError(e: Throwable) {
                        Log.d("AppLogs", "onError, $e")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.d("AppLogs", "onSubscribe")
                        disposable = d
                    }

                    override fun onNext(t: Int) {
                        Log.d("AppLogs", "ReceiveNext: $t")
                    }

                    override fun onComplete() {
                        Log.d("AppLogs", "onComplete")
                    }

                })

            connectableObservable1.connect()

        }
    }
}
