package com.macgavrina.subject

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val source1 = PublishSubject.create<Int>()
        val source2 = PublishSubject.create<Int>()
        val bothSources = PublishSubject.create<Int>()

        button_next1.setOnClickListener { _ ->
            source1.onNext(123)
        }

        button_complete1.setOnClickListener { _ ->
            source1.onComplete()
        }

        button_next2.setOnClickListener { _ ->
            source2.onNext(456)
        }

        button_complete1.setOnClickListener { _ ->
            source2.onComplete()
        }

        button_subscribe.setOnClickListener { _ ->

            source1.subscribe(bothSources)
            source2.subscribe(bothSources)

            bothSources
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : Observer<Int> {

                    override fun onError(e: Throwable) {
                        Log.d("AppLogs", "onError, $e")
                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.d("AppLogs", "onSubscribe")
                    }

                    override fun onNext(t: Int) {
                        Log.d("AppLogs", "ReceiveNext: $t")
                    }

                    override fun onComplete() {
                        Log.d("AppLogs", "onComplete")
                    }

                })
        }

    }

}

