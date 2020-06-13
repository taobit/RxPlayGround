package com.pluralsight.rxjava2.module3

import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.exitProcess

fun main() {
    val numberOfAlphas = AtomicInteger()
    GreekAlphabet.greekAlphabetInEnglishObservable()
            .repeat(3)
            .takeWhile {
                if (it == "Alpha") {
                    return@takeWhile numberOfAlphas.incrementAndGet() != 2
                }
                return@takeWhile true
            }
            .subscribe(DemoSubscriber<String>())
    exitProcess(0)
}