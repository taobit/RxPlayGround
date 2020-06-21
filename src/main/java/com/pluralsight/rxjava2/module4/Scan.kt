package com.pluralsight.rxjava2.module4

import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.utility.datasets.FibonacciSequence
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber

fun main() {
    FibonacciSequence.create(12)
            .scan<Long>(0L,
                    { current, next ->
                        val sum = current + next
                        log.info("$current + $next = $sum")
                        sum
                    })
            .subscribe(DemoSubscriber<Long>())
}