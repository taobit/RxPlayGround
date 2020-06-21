package com.pluralsight.rxjava2.module4

import com.pluralsight.rxjava2.utility.datasets.FibonacciSequence
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import kotlin.system.exitProcess


fun main() {

    // Create a Fibonacci observable to 20 values.
    // Because we are going to buffer the results, we will expect
    // a Observable<List<Long>> instead of Observable<Long>.
    FibonacciSequence.create(20) // Emit items 3 at a time.
            .buffer(3)
            .subscribe(DemoSubscriber())
    exitProcess(0)
}
