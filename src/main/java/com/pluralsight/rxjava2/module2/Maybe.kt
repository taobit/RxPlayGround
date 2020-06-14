package com.pluralsight.rxjava2.module2

import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import io.reactivex.rxjava3.core.MaybeObserver
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import kotlin.system.exitProcess

fun main() {
    Observable.fromArray(*GreekAlphabet.greekLetters)
            .firstElement()
            .filter { it == "\u03b1" }
            .also {
                it.subscribe(object : MaybeObserver<String> {
                    override fun onSuccess(t: String) {
                        log.info("onSuccess $t")
                        gate.openGate("onSuccess")
                    }

                    override fun onComplete() {
                        log.error("onComplete")
                        gate.openGate("onComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        log.info("onSubscribe")
                    }

                    override fun onError(e: Throwable) {
                        log.error("onError", e)
                        gate.openGate("onError")
                    }

                })
            }
    gate.waitForAny("onSuccess", "onComplete", "onError")
    exitProcess(0)
}