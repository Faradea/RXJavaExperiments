package com.macgavrina.rxjavaexperiments

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.View
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar.visibility = View.INVISIBLE

        generate_event_fab.setOnClickListener { view ->

            //JustObservable().letsGo()
            //CompletableObservable().letsGo()
            letsGo()
            //JustObservable().observerAndObservableInOne()
        }

        get_disposable_state.setOnClickListener { view ->
            Log.d("AppLogs","isDisposed = ${disposable.isDisposed}")
        }
    }




    private fun letsGo() {
//        val singleObservable = SingleObservableWithStringResult().createObservableFromActionWithResult()
//        subscribeWithToEvents(singleObservable)

//        val observableWithEmitter = ObservableWithEmitter().createObservableWithEmitter()
//        subscribeToObservableWithEmitter(observableWithEmitter)

//        val timerObservable = TimerObservable().createTimerObservable()
//        subscribeToTimerObservable(timerObservable)

        val just12345Observable = JustObservable().createObservableWithJust12345()
        val filteredObservable = filter(just12345Observable)
        subscribeToObservable123(filteredObservable)
    }

    private fun filter(just12345Observable: Observable<Int>): Observable<Int>? {

        val filteredObservable = just12345Observable
            .filter { num -> num < 3 }

        return filteredObservable
    }

    private fun subscribeWithToEvents(observable: Single<String>) {

        var disposableObserver = observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : SingleObserver<String> {

                override fun onSubscribe(d: Disposable) {
                    progressBar.visibility = View.VISIBLE
                    disposable = d
                }

                override fun onSuccess(t: String) {
                    Log.d("AppLogs", "onSuccess, t = $t")
                    progressBar.visibility = View.INVISIBLE
                    Log.d("AppLogs","isDisposed = ${disposable.isDisposed}")

                }

                override fun onError(e: Throwable) {
                    Log.d("AppLogs", "Error, $e")
                    progressBar.visibility = View.INVISIBLE
                }

            })
    }

    private fun subscribeToObservableWithEmitter(observable: Observable<String>?) {

        if (observable == null) return

        lateinit var disposable: Disposable

        var observer = observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : Observer<String> {

                override fun onError(e: Throwable) {
                    Log.d("AppLogs", "onError, $e")
                }

                override fun onSubscribe(d: Disposable) {
                    Log.d("AppLogs", "onSubscribe")
                    disposable = d
                }

                override fun onNext(t: String) {
                    Log.d("AppLogs", "ReceiveNext: $t")
                }

                override fun onComplete() {
                    Log.d("AppLogs", "onComplete")
                }

            })
    }

    private fun subscribeToObservable123(observable: Observable<Int>?) {

        if (observable == null) return

        lateinit var disposable: Disposable

        var observer = observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
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
    }

    private fun subscribeToTimerObservable(timerObservable: Observable<Long>?) {

        if (timerObservable == null) return

        timerObservable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object: Observer<Long>{
                override fun onError(e: Throwable) {
                    Log.d("AppLogs", "onError, $e")
                    progressBar.visibility = View.INVISIBLE
                }

                override fun onSubscribe(d: Disposable) {
                    progressBar.visibility = View.VISIBLE
                    Log.d("AppLogs", "onSubscribe")
                    disposable = d
                }

                override fun onNext(t: Long) {
                    Log.d("AppLogs", "ReceiveNext: $t")
                }

                override fun onComplete() {
                    Log.d("AppLogs", "onComplete")
                    progressBar.visibility = View.INVISIBLE
                }

            })
    }
}
