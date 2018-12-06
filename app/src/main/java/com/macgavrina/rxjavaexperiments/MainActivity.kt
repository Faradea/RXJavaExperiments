package com.macgavrina.rxjavaexperiments

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar.visibility = View.INVISIBLE

        generate_event_fab.setOnClickListener { view ->

            //JustObservable().letsGo()
            //CompletableObservable().letsGo()
            letsGo()
        }
    }

    private fun letsGo() {
        val singleObservable = SingleObservableWithStringResult().createObservableFromActionWithResult()
        subscribeWithToEvents(singleObservable)
    }


    private fun subscribeWithToEvents(observable: Single<String>) {
        var disposableObserver = observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : SingleObserver<String> {

                override fun onSubscribe(d: Disposable) {
                    progressBar.visibility = View.VISIBLE
                }

                override fun onSuccess(t: String) {
                    Log.d("AppLogs", "onSuccess, t = $t")
                    progressBar.visibility = View.INVISIBLE
                }

                override fun onError(e: Throwable) {
                    Log.d("AppLogs", "Error, $e")
                    progressBar.visibility = View.INVISIBLE
                }

            })
    }
}
