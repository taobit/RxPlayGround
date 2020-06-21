package com.pluralsight.rxjava2.module4

import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber

fun main() {
    GreekAlphabet.greekAlphabetInEnglishObservable()
            .map { it.length }
            .subscribe(DemoSubscriber<Int>())
}