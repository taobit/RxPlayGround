package com.pluralsight.rxjava2.module2

import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import com.pluralsight.rxjava2.utility.datasets.GreekLetterPair
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.BiFunction
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.exitProcess

fun main() {
    val counter = AtomicInteger()

    Observable.zip(
        GreekAlphabet.greekAlphabetInGreekObservable(),
        GreekAlphabet.greekAlphabetInEnglishObservable(),
        BiFunction { greek: String, english: String ->
            if (counter.incrementAndGet() >= 5) {
                throw IllegalStateException("BOOM")
            }
            GreekLetterPair(greek, english)
        }
    ).also {
        it.onErrorResumeNext {
            Observable.just(GreekLetterPair("κεραία", "BOOM"))
        }.subscribe(object : Observer<GreekLetterPair> {
            override fun onComplete() = gate.onComplete()

            override fun onSubscribe(d: Disposable) = log.info("onSubscribe")

            override fun onNext(t: GreekLetterPair) = log.info("OnNext : ${t.englishLetter}, ${t.greekLetter}")

            override fun onError(e: Throwable) = gate.onError(e)

        })
    }
    gate.waitForAny("onComplete", "onError")
    exitProcess(0)
}