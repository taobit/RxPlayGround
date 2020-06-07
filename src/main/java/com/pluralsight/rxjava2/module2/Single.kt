package com.pluralsight.rxjava2.module2

import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlin.system.exitProcess

/*
 * no onNext
 * no onComplete
 */
fun main() {
    // only take the first letter
    Observable.fromArray(*GreekAlphabet.greekLetters).first("A").also {
        it.subscribe(object : SingleObserver<String> {
            override fun onSuccess(t: String) {
                log.info("onSuccess $t")
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
    gate.waitForAny("onSuccess", "onError")
    exitProcess(0)
}