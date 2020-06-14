package com.pluralsight.rxjava2.module3

import com.pluralsight.rxjava2.utility.sleep
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

fun main() {

    // Create a repeating greek alphabet
    val incrementingObservable = Observable.interval(0L, 50L, TimeUnit.MILLISECONDS) // Like timeout, sample must use a different thread pool
            // so that it can send a message event though events
            // may be being generated on the main thread.
            .subscribeOn(Schedulers.computation()) // Sample the stream every 2 seconds.
            .sample(100, TimeUnit.MILLISECONDS)

    // Subscribe and watch the emit happen every 2 seconds.
    incrementingObservable.subscribe(DemoSubscriber())

    // Wait for 10 seconds
    sleep(10, TimeUnit.SECONDS)
    exitProcess(0)
}
