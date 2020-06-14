package com.pluralsight.rxjava2.module2

import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import com.pluralsight.rxjava2.utility.datasets.GreekLetterPair
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlin.system.exitProcess

fun main() {
    fun Observable<GreekLetterPair>.subscribeToZipObservable() {
        doOnSubscribe {
            log.info("onSubscribe")
        }
        subscribeBy(
            onComplete = { gate.onComplete() },
            onNext = {
                log.info("onNext : ${it.englishLetter}, ${it.greekLetter}")
            },
            onError = { gate.onError(it) }
        )
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