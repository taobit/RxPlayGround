package com.pluralsight.rxjava2.module2

import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlin.system.exitProcess

/*
 * no onNext
 * no onComplete
 */
fun main() {
    // only take the first letter
    Observable.fromArray(*GreekAlphabet.greekLetters)
        .first("A")
        .also { single ->
            single.doOnSubscribe {
                log.info("onSubscribe")
            }.subscribeBy(
                onSuccess = { gate.onSuccess(it) },
                onError = { gate.onError(it) }
            )
        }
    gate.waitForAny("onSuccess", "onError")
    exitProcess(0)
}