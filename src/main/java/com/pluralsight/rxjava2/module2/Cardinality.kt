package com.pluralsight.rxjava2.module2

import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import com.pluralsight.rxjava2.utility.subscribers.*
import io.reactivex.rxjava3.core.Observable

fun main() {

    runCode("Single") {
        Observable.fromArray(*GreekAlphabet.greekLetters)
                .first("?")
                .subscribe({ SingleDemoSubscriber<String>(gate) },
                        { gate.onError(it) }
                )
    }

    gate.resetAll()

    runCode("Maybe") {
        Observable.fromArray(*GreekAlphabet.greekLetters)
                .first("?")
                .filter { it == "\u03b1" }
                .doOnSubscribe {
                    gate.onSubscribe()
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
                    gate.onSubscribe()
                }.subscribe(
                        { gate.onSuccess(it) },
                        { gate.onError(it) },
                        { gate.onComplete() }
                )
    }

    runCode("Complete") {
        Observable.fromArray(*GreekAlphabet.greekLetters)
                .ignoreElements()
                .doOnSubscribe {
                    gate.onSubscribe()
                }.subscribe(
                        { gate.onComplete() },
                        { gate.onError(it) }
                )
    }
}