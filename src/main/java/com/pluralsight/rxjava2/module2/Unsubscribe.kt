package com.pluralsight.rxjava2.module2

import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.exitProcess

fun main() {
    val counter = AtomicInteger(0)
    Observable.fromArray(*GreekAlphabet.greekLetters)
            .subscribe(object : Observer<String> {

                private var disposable: Disposable? = null

                override fun onComplete() {
                    log.info("onComplete")
                    gate.openGate("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                    log.info("onSubscribe")
                }

                override fun onNext(letter: String) {
                    log.info("OnNext : $letter")
                    if (counter.incrementAndGet() >= 5) {
                        disposable?.dispose()
                        gate.openGate("eventMaxReached")
                    }
                }

                override fun onError(e: Throwable) {
                    log.error(e.message)
                    gate.openGate("onError")
                }

            })
    gate.waitForAny("eventMaxReached", "onComplete", "onError")
    log.info("gate status : ${gate.isGateOpen("eventMaxReached")}")
    log.info("gate status : ${gate.isGateOpen("onComplete")}")
    log.info("gate status : ${gate.isGateOpen("onError")}")
    exitProcess(0)
}