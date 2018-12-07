package com.macgavrina.combining

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar.isIndeterminate = true
        progressBar.visibility = View.INVISIBLE

        result_tv.text = ""

        button_startA.setOnClickListener {
            val observableA = MyObservableWithEmitter().createObservableWithEmitterA()

            if (observableA != null) {
                subscribeWithToEvents(observableA)
            }
        }

        button_startB.setOnClickListener {
            val observableB = MyObservableWithEmitter().createObservableWithEmitterB()

            if (observableB != null) {
                subscribeWithToEvents(observableB)
            }
        }

        button_startAB.setOnClickListener {
            val observableA = MyObservableWithEmitter().createObservableWithEmitterA()
            val observableB = MyObservableWithEmitter().createObservableWithEmitterB()

            val observableC = observableA!!.mergeWith(observableB)
            subscribeWithToEvents(observableC)
        }
    }

    private fun subscribeWithToEvents(observable: Observable<String>) {

        var disposableObserver = observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : Observer<String> {
                override fun onNext(t: String) {
                    Log.d("AppLogs", "onNext, $t")
                    result_tv.text = t
                }

                override fun onComplete() {
                    Log.d("AppLogs", "onComplete")
                    progressBar.visibility = View.INVISIBLE
                    result_tv.text = "Done"
                }

                override fun onSubscribe(d: Disposable) {
                    progressBar.visibility = View.VISIBLE
                    Log.d("AppLogs", "onSubscribe")
                    result_tv.text = ""
                }

                override fun onError(e: Throwable) {
                    Log.d("AppLogs", "onError, $e")
                    progressBar.visibility = View.INVISIBLE
                    result_tv.text = "Error"
                }

            })
    }
}
