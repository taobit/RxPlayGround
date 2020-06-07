package com.pluralsight.rxjava2.module2

import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlin.system.exitProcess

fun main() {
    Observable.fromArray(*GreekAlphabet.greekLetters)
            .subscribe(object : Observer<String> {
                override fun onComplete() {
                    log.debug("onComplete")
                    gate.openGate("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    log.debug("onSubscribe")
                }

                override fun onError(e: Throwable) {
                    log.error(e.message)
                    gate.openGate("onError")
                }

                override fun onNext(t: String) {
                    log.debug("onNext $t")
                }
            })
    gate.waitForAny("onComplete", "onError")
    exitProcess(0)
}
