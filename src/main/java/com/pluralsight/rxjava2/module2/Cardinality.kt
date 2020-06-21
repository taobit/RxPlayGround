package com.pluralsight.rxjava2.module2

import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import com.pluralsight.rxjava2.utility.subscribers.SingleDemoSubscriber
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy

fun main() {

    runCode("Single") {
        Observable.fromArray(*GreekAlphabet.greekLetters)
                .first("?")
                .subscribe({ SingleDemoSubscriber<String>() },
                        { gate.onError(it) }
                )
    }

    gate.resetAll()

    runCode("Maybe") {
        Observable.fromArray(*GreekAlphabet.greekLetters)
                .first("?")
                .filter { it == "\u03b1" }
                .doOnSubscribe {
                    gate.onComplete()
                }.subscribe(
                        { gate.onSuccess(it) },
                        { gate.onError(it) },
                        { gate.onComplete() }
                )
    }
    runCode("MaybeNo") {
        Observable.fromArray(*GreekAlphabet.greekLetters)
            .first("?")
            .filter { it != "\u03b1" }
            .doOnSubscribe {
                logOnSubscribe()
            }.subscribeBy(
                onComplete = { gate.onComplete() },
                onSuccess = { gate.onSuccess(it) },
                onError = { gate.onError(it) }
            )
    }

    runCode("Complete") {
        Observable.fromArray(*GreekAlphabet.greekLetters)
                .ignoreElements()
                .doOnSubscribe {
                    logOnSubscribe()
                }.subscribe(
                        { gate.onComplete() },
                        { gate.onError(it) }
                )
    }
}