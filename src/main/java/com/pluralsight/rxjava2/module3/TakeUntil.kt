package com.pluralsight.rxjava2.module3

import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

fun main() {
    GreekAlphabet.greekAlphabetInEnglishObservable()
            .repeat()
            .takeUntil(Observable.interval(2, 10, TimeUnit.SECONDS))
            .subscribe(DemoSubscriber<String>())
    exitProcess(0)
}