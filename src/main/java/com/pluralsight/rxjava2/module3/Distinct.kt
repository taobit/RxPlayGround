package com.pluralsight.rxjava2.module3

import com.pluralsight.rxjava2.module2.runCode
import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import kotlin.system.exitProcess

fun main() {
    runCode("Example 1") {
        GreekAlphabet.greekAlphabetInEnglishObservable()
                .repeat(3)
                .distinct()
                .subscribe(DemoSubscriber<String>())
    }
    runCode("Example 2") {
        GreekAlphabet.greekAlphabetInEnglishObservable()
                .repeat(3)
                .distinct { it[0] }
                .subscribe(DemoSubscriber<String>())
    }
    exitProcess(0)
}