package com.pluralsight.rxjava2.module4

import com.pluralsight.rxjava2.module2.*
import com.pluralsight.rxjava2.utility.datasets.FibonacciSequence
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.rxjava3.schedulers.Schedulers

fun main() {
    val observable = FibonacciSequence.create(10)
            .doOnSubscribe { logOnSubscribe() }

    runCode("") {
        observable.subscribe(DemoSubscriber<Long>())
        gate.waitForAny(OnError, OnComplete)
    }
    gate.resetAll()
    runCode("") {
        observable.subscribeOn(Schedulers.computation())
                .subscribeOn(Schedulers.io())// ignore
                .subscribe(DemoSubscriber<Long>())
        gate.waitForAny(OnError, OnComplete)
    }
    gate.resetAll()
    runCode("") {
        observable.observeOn(Schedulers.computation())
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .subscribe(DemoSubscriber<Long>())
        gate.waitForAny(OnError, OnComplete)
    }
}
