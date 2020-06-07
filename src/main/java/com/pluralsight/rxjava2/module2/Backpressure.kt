package com.pluralsight.rxjava2.module2

import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

fun main() {
    val range = Observable.range(1, 1_000_000_000)
            .repeat()
            .doOnNext { log.info("emitting int : $it") }
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())

    DemoSubscriber<Int>(gate, onNextDelayDuration = 10L, onNextDelayTimeUnit = TimeUnit.MILLISECONDS).also {
        range.subscribe(it)
    }

    gate.waitForAny("onError", "onComplete")
    exitProcess(0)
}