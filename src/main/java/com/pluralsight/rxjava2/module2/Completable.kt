package com.pluralsight.rxjava2.module2

import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import io.reactivex.rxjava3.core.Observable
import kotlin.system.exitProcess


fun main() {
    Observable.fromArray(*GreekAlphabet.greekLetters)
            .doOnNext { log.info("DoOnNext : $it") }
            .ignoreElements()
            .also { completable ->
                completable.doOnSubscribe {
                    logOnSubscribe()
                }.subscribe(
                        { gate.onComplete() },
                        { gate.onError(it) }
                )
            }
    gate.waitForAny(OnComplete, OnError)
    exitProcess(0)
}