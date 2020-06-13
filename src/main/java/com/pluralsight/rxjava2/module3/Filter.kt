package com.pluralsight.rxjava2.module3

import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.module2.logError
import com.pluralsight.rxjava2.module2.logNext
import com.pluralsight.rxjava2.module2.runCode
import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber

fun main() {
    runCode("Example 1") {
        GreekAlphabet.greekAlphabetInEnglishObservable()
                .filter { it != "delta" }
                .subscribe(DemoSubscriber())
    }
    runCode("Example 2") {
        GreekAlphabet.greekAlphabetInEnglishObservable()
                .filter { it != "delta" }
                .subscribe(
                        { logNext(it) },
                        { log.error(it.message) },
                        { log.info("onComplete") }
                )
    }
    runCode("Example 3") {
        GreekAlphabet.greekAlphabetInEnglishObservable()
                .filter { it.toLowerCase() != "delta" }
                .subscribe({
                    logNext(it)
                    if (it == "iota") {
                        throw IllegalStateException("BOOM!")
                    }
                },
                        { logError(it) })
    }
}