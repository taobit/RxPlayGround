package com.pluralsight.rxjava2.module3

import com.pluralsight.rxjava2.utility.ThreadHelper
import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

fun main() {
    GreekAlphabet.greekAlphabetInEnglishObservable()
            .repeat()
            .skipUntil(
                Observable.interval(2, 10, TimeUnit.SECONDS)
            )
            .subscribeOn(Schedulers.newThread())
            .subscribe(DemoSubscriber<String>())
    ThreadHelper.sleep(3, TimeUnit.SECONDS)
    exitProcess(0)
}