package com.pluralsight.rxjava2.module3

import com.pluralsight.rxjava2.module2.runCode
import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import java.util.concurrent.atomic.AtomicInteger

fun main() {
    runCode("Example 1") {
        val numOfAlpha = AtomicInteger()
        GreekAlphabet.greekAlphabetInEnglishObservable()
                .repeat(3)
                .skipWhile {
                    if (it == "alpha") {
                        // if count to 3 times, the letter won't be skipped
                        return@skipWhile numOfAlpha.incrementAndGet() != 3
                    }
                    return@skipWhile false
                }
                .subscribe(DemoSubscriber<String>())
    }
}