package com.pluralsight.rxjava2.module5

import com.pluralsight.rxjava2.module2.*
import com.pluralsight.rxjava2.utility.GateBasedSynchronization
import com.pluralsight.rxjava2.utility.datasets.FibonacciSequence
import com.pluralsight.rxjava2.utility.sleep
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun main() {
    runCode {
        val gate1 = GateBasedSynchronization()
        val gate2 = GateBasedSynchronization()

        val fib = FibonacciSequence.create(20)
                .subscribeOn(Schedulers.computation())
                .publish()
        with(fib) {
            subscribe(DemoSubscriber<Long>(gate1))
            subscribe(DemoSubscriber<Long>(gate2))
        }

        sleep(2, TimeUnit.SECONDS)
        fib.connect()
        GateBasedSynchronization.waitMultiple(arrayOf(OnComplete, OnError), gate1, gate2)
    }

    runCode {
        val subscriber1 = DemoSubscriber<Long>()
        val subscriber2 = DemoSubscriber<Long>()

        Observable.interval(100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .doOnNext { logNext(it) }
                .publish()
                .refCount()
                .apply {
                    subscribe(subscriber1)
                    subscribe(subscriber2)
                }
        sleep(2, TimeUnit.SECONDS)
        subscriber1.dispose()
        sleep(2, TimeUnit.SECONDS)
        subscriber2.dispose()
        log.info("Pause 2s")
        sleep(2, TimeUnit.SECONDS)
        log.info("resume")
        sleep(2, TimeUnit.SECONDS)
    }
    runCode {
        val subscriber1 = DemoSubscriber<Long>()
        val subscriber2 = DemoSubscriber<Long>()
        Observable.interval(100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .doOnNext { logNext(it) }
                .share()
                .apply {
                    subscribe(subscriber1)
                    subscribe(subscriber2)
                }
        sleep(2, TimeUnit.SECONDS)
        subscriber1.dispose()
        sleep(2, TimeUnit.SECONDS)
        subscriber2.dispose()
        log.info("Pause 2s")
        sleep(2, TimeUnit.SECONDS)
        log.info("resume")
        sleep(2, TimeUnit.SECONDS)
    }
}