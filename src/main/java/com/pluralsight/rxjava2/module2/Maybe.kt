package com.pluralsight.rxjava2.module2

import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlin.system.exitProcess

fun main() {
    Observable.fromArray(*GreekAlphabet.greekLetters)
        .firstElement()
        .filter { it == "\u03b1" }
        .also { maybe ->
            maybe.doOnSubscribe {
                log.info("onSubscribe")
            }.subscribeBy(
                onSuccess = { gate.onSuccess(it) },
                onComplete = { gate.onComplete() },
                onError = { gate.onError(it) }
            )
        }
    gate.waitForAny("onSuccess", "onComplete", "onError")
    exitProcess(0)
}