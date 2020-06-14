package com.pluralsight.rxjava2.module2

import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import com.pluralsight.rxjava2.utility.datasets.GreekLetterPair
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.BiFunction
import kotlin.system.exitProcess

fun main() {
    fun Observable<GreekLetterPair>.subscribeToZipObservable() {
        subscribe(object : Observer<GreekLetterPair> {
            override fun onComplete() {
                log.info("onComplete")
                gate.openGate("onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                log.info("onSubscribe")
            }

            override fun onNext(t: GreekLetterPair) {
                log.info("onNext : ${t.englishLetter}, ${t.greekLetter}")
            }

            override fun onError(e: Throwable) {
                log.error(e.message)
            }
        })
    }

    Observable.zip(
            GreekAlphabet.greekAlphabetInGreekObservable(),
            GreekAlphabet.greekAlphabetInEnglishObservable(),
            BiFunction { greek: String, english: String -> GreekLetterPair(greek, english) }
    ).also {
        it.subscribeToZipObservable()
    }
    with(gate) {
        waitForAny("onComplete", "onError")
        resetAll()
    }
    exitProcess(0)
}