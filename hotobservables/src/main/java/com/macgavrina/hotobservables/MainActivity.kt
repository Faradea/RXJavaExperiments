package com.macgavrina.hotobservables

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import io.reactivex.observables.ConnectableObservable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import android.graphics.Bitmap
import io.reactivex.Observer
import io.reactivex.internal.disposables.DisposableHelper.dispose
import org.reactivestreams.Subscriber




class MainActivity : AppCompatActivity() {

    lateinit var connectableObservable: ConnectableObservable<Int>
    lateinit var subscription: Observer<Int>
    lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar.visibility = View.INVISIBLE

        button_start_events.setOnClickListener { view ->

            val observable = Observable.create<Int> { emitter ->
                emitter.onNext(1)
                TimeUnit.SECONDS.sleep(3)
                emitter.onNext(2)
                TimeUnit.SECONDS.sleep(3)
                //emitter.onError(Throwable("Sample error"));
                emitter.onNext(3)
                TimeUnit.SECONDS.sleep(3)
                emitter.onComplete()
            }

            progressBar.visibility = View.VISIBLE

            connectableObservable = observable
                .subscribeOn(Schedulers.computation())
                .publish()

        }

        button_subscribe.setOnClickListener { view ->
            if (::connectableObservable.isInitialized) {
                Log.d("AppLogs","connecting...")

                subscription = connectableObservable
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
                        progressBar.visibility = View.INVISIBLE
                    }

                })

                connectableObservable.connect()

            }
        }

        button_unsubscribe.setOnClickListener { _ ->
            if (::connectableObservable.isInitialized && ::disposable.isInitialized && !disposable.isDisposed) {
                disposable.dispose()
            }
        }
    }
}
