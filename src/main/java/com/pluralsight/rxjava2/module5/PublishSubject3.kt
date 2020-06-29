package com.pluralsight.rxjava2.module5

import com.pluralsight.rxjava2.module2.*
import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import com.pluralsight.rxjava2.utility.sleep
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

fun main() {
    val subject = PublishSubject.create<String>().also { subject ->
        GreekAlphabet.greekAlphabetInEnglishObservable()
                .subscribeOn(Schedulers.computation())
                .subscribeBy(
                        onNext = {
                            subject.onNext(it)
                            sleep(250, TimeUnit.MILLISECONDS)
                        },
                        onError = { gate.onError(it) },
                        onComplete = { gate.onComplete() }
                )
    }.apply {
        subscribeOn(Schedulers.computation())
                .subscribe {
                    logNext(it)
                    if (it == "eta") {
                        gate.openGate(PROCEED)
                    }
                }
    }
    gate.waitForAny(PROCEED)
    subject.subscribeOn(Schedulers.computation())
            .subscribe(DemoSubscriber<String>())
    gate.waitForAny(OnComplete, OnError)
}

const val PROCEED = "Proceed"