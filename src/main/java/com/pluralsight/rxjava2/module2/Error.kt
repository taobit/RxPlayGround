package com.pluralsight.rxjava2.module2

import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import com.pluralsight.rxjava2.utility.datasets.GreekLetterPair
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.exitProcess

fun main() {
    val counter = AtomicInteger()

    Observable.zip(
            GreekAlphabet.greekAlphabetInGreekObservable(),
            GreekAlphabet.greekAlphabetInEnglishObservable(),
            BiFunction { it1: String, it2: String ->
                if (counter.incrementAndGet() >= 5) {
                    throw IllegalStateException("BOOM")
                }
                GreekLetterPair(it1, it2)
            }
    ).also {
        it.onErrorResumeNext(Observable.just(GreekLetterPair("κεραία", "BOOM")))
                .subscribe(object : Observer<GreekLetterPair> {
                    override fun onComplete() {
                        log.info("onComplete")
                        gate.openGate("onComplete")
                    }

                    override fun onSubscribe(d: Disposable) = log.info("onSubscribe")

                    override fun onNext(t: GreekLetterPair) = log.info("OnNext : ${t.englishLetter}, ${t.greekLetter}")

                    override fun onError(e: Throwable) {
                        log.error(e.message)
                        gate.openGate("onError")
                    }

                })
    }

    gate.waitForAny("onComplete", "onError")
    exitProcess(0)
}