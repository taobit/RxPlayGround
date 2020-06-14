package com.pluralsight.rxjava2.module3

import com.pluralsight.rxjava2.module2.gate
import com.pluralsight.rxjava2.module2.runCode
import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import com.pluralsight.rxjava2.utility.sleep
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun main() {
    runCode("timeout emits the \"timeout\" onError on the computation thread pool") {
        Observable.create<String> {
            it.onNext(GreekAlphabet.greekLettersInEnglish[0])
            it.onNext(GreekAlphabet.greekLettersInEnglish[1])
            sleep(1, TimeUnit.DAYS)
            it.onNext(GreekAlphabet.greekLettersInEnglish[2])
            it.onComplete()
        }.subscribeOn(Schedulers.computation())
                .timeout(2, TimeUnit.SECONDS)
                .subscribe(DemoSubscriber<String>())
        gate.waitForAny("onComplete", "onError")
    }
    gate.resetAll()
    runCode("") {
        Observable.create<String> {
            sleep(1, TimeUnit.DAYS)
            GreekAlphabet.greekLettersInEnglish.forEach { letter ->
                it.onNext(letter)
            }
            it.onComplete()
        }.subscribeOn(Schedulers.computation())
                .timeout(2, TimeUnit.SECONDS,
                        GreekAlphabet.greekAlphabetInEnglishObservable())
                .subscribe(DemoSubscriber<String>())
        gate.waitForAny("onComplete", "onError")
    }
}