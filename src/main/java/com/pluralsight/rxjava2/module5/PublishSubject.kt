package com.pluralsight.rxjava2.module5

import com.pluralsight.rxjava2.utility.datasets.FibonacciSequence
import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import com.pluralsight.rxjava2.utility.sleep
import com.pluralsight.rxjava2.utility.subjects.SelectableSubject
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

fun main() {
    SelectableSubject<String>().apply {
        addEventConsumer(DemoSubscriber<String>())
        addEventProducer(
                GreekAlphabet.greekAlphabetInEnglishObservable()
                        .subscribeOn(Schedulers.computation())
        )
        addEventProducer(FibonacciSequence.create(20)
                .repeat()
                .map { it.toString() }
                .subscribeOn(Schedulers.computation()))
    }
    sleep(10, TimeUnit.SECONDS)
    exitProcess(0)
}