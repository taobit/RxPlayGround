package com.pluralsight.rxjava2.module2

import com.pluralsight.rxjava2.utility.GateBasedSynchronization
import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import io.reactivex.rxjava3.core.Observable
import kotlin.system.exitProcess


fun main() {
    val gate = GateBasedSynchronization()
    Observable.fromArray(*GreekAlphabet.greekLetters)
            .doOnNext { log.info("DoOnNext : $it") }
            .ignoreElements()
            .also { completable ->
                completable.doOnSubscribe {
                    com.pluralsight.rxjava2.module2.gate.onSubscribe()
                }.subscribe(
                        { com.pluralsight.rxjava2.module2.gate.onComplete() },
                        { com.pluralsight.rxjava2.module2.gate.onError(it) }
                )
            }
    gate.waitForAny("onComplete", "OnError")
    exitProcess(0)
}