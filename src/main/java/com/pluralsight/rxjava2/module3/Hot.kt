package com.pluralsight.rxjava2.module3

import com.pluralsight.rxjava2.module2.gate
import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.utility.ThreadHelper
import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet.greekAlphabetInEnglishHotObservable
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

fun main() {

    // Create a "hot" observable that emits greek letters at a furious pace.
    // We only take the first 49 events to keep things understandable.
    val hotGreekAlphabet = greekAlphabetInEnglishHotObservable(true)
            .take(5)

    // Sleep for 2 seconds to give the hot observable a chance to run.
    ThreadHelper.sleep(2, TimeUnit.SECONDS)

    // Setup a subscriber
    val subscriber = DemoSubscriber<String>(gate)

    // Subscribe to the hot stream of greek letters.
    log.info("Subscribing now...")
    hotGreekAlphabet.subscribe(subscriber)

    // Wait for 2 seconds, or until one of the gates is opened.
    log.info("Wait for subscriber to signal that it is finished.")
    gate.waitForAny("onComplete", "onError")
    exitProcess(0)
}
