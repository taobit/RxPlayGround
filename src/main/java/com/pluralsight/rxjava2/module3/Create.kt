package com.pluralsight.rxjava2.module3

import com.pluralsight.rxjava2.module2.gate
import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

fun main() {

    createGeometricSequence(1, 2, 8)
            .subscribe(DemoSubscriber<Int>())
    log.info("make observable on separate thread")
    createGeometricSequence(3, 3, 6)
            .subscribeOn(Schedulers.computation())
            .subscribe(DemoSubscriber<Int>())
    gate.waitForAny("onComplete", "onError")
}

fun createGeometricSequence(start: Int, multiplier: Int, totalNumber: Int): Observable<Int> {
    if (start == 0) {
        throw IllegalStateException("can't start from 0")
    }
    return Observable.create {
        var count = 0
        var current = start

        while (count < totalNumber) {
            if (it.isDisposed) {
                break
            }
            count++
            it.onNext(current)
            current *= multiplier
        }
        if (!it.isDisposed) {
            it.onComplete()
        }
    }
}