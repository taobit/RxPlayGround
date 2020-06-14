package com.pluralsight.rxjava2.module3

import com.pluralsight.rxjava2.module2.gate
import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet.greekAlphabetInEnglishObservable
import com.pluralsight.rxjava2.utility.sleep
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

fun main() {

    // Create a "cold" observable that emits the Greek alphabet using English words.
    // We want the stream to keep repeating until we unsubscribe, but not more than
    // 49 events since we don't want to overflow our output window for illustration purposes.
    val coldGreekAlphabet = greekAlphabetInEnglishObservable()
            .repeat()
            .take(49)
            .subscribeOn(Schedulers.newThread())

    // Sleep for 2 seconds to give the observable time to run if it's going to...
    // but it's not since it's a cold observable.
    sleep(2, TimeUnit.SECONDS)

    // Setup a subscriber
    log.info("Subscribing...")
    val subscriber = DemoSubscriber<String>(gate)

    // Subscribe to the stream of greek letters.
    coldGreekAlphabet.subscribe(subscriber)

    // Let it run for 2 seconds, or until it's taken 49 items and opens the onComplete gate.
    log.info("Wait for subscriber to signal that it has finished.")
    gate.waitForAny("onComplete", "onError")
    exitProcess(0)
}
