package com.pluralsight.rxjava2.module2

import com.pluralsight.rxjava2.utility.GateBasedSynchronization
import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import io.reactivex.CompletableObserver
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlin.system.exitProcess


fun main() {
    val gate = GateBasedSynchronization()
    Observable.fromArray(*GreekAlphabet.greekLetters)
            .doOnNext { log.info("DoOnNext : $it") }
            .ignoreElements()
            .also {
                it.subscribe(
                        object : CompletableObserver {
                            override fun onComplete() {
                                log.info("OnComplete")
                                gate.openGate("OnComplete")
                            }

                            override fun onSubscribe(d: Disposable) {
                                log.info("OnSubscribe")
                            }

                            override fun onError(e: Throwable) {
                                log.error(e.message)
                                gate.openGate("OnError")
                            }

                        }
                )
            }
    gate.waitForAny("onComplete", "OnError")
    exitProcess(0)
}