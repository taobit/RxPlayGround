package com.pluralsight.rxjava2.module3

import com.pluralsight.rxjava2.module2.runCode
import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber

fun main() {
    runCode("Example 1") {
        GreekAlphabet.greekAlphabetInEnglishObservable()
                .filter { it != "delta" }
                .subscribe(DemoSubscriber())
    }
}