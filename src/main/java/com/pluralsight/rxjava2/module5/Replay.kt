package com.pluralsight.rxjava2.module5

import com.pluralsight.rxjava2.utility.datasets.FibonacciSequence
import com.pluralsight.rxjava2.utility.sleep
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.ReplaySubject
import java.util.concurrent.TimeUnit

fun main() {
    ReplaySubject.createWithSize<Long>(20).apply {
        FibonacciSequence.create(30)
                .subscribeOn(Schedulers.computation())
                .subscribe { onNext(it) }
        sleep(1, TimeUnit.SECONDS)
        subscribeOn(Schedulers.computation())
                .subscribe(DemoSubscriber<Long>())
        sleep(1, TimeUnit.SECONDS)
        subscribeOn(Schedulers.computation())
                .subscribe(DemoSubscriber<Long>())
        sleep(2, TimeUnit.SECONDS)
    }
}