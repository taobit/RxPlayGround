package com.pluralsight.rxjava2.module2

import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import com.pluralsight.rxjava2.utility.subscribers.DemoCompletableObserver
import com.pluralsight.rxjava2.utility.subscribers.MaybeDemoSubscriber
import com.pluralsight.rxjava2.utility.subscribers.SingleDemoSubscriber
import io.reactivex.Observable

fun main() {
    val firstLetter = Observable.fromArray(*GreekAlphabet.greekLetters).first("?")
    val maybeFirstLetter = Observable.fromArray(*GreekAlphabet.greekLetters).first("?").filter { it == "\u03b1" }
    val maybeNoLetter = Observable.fromArray(*GreekAlphabet.greekLetters).first("?").filter { it != "\u03b1" }
    val completable = Observable.fromArray(*GreekAlphabet.greekLetters).ignoreElements()

    log.info("Single")
    firstLetter.subscribe( SingleDemoSubscriber(gate, "onError", "onSuccess"))
    gate.resetAll()
    log.info("Maybe")
    maybeFirstLetter.subscribe(MaybeDemoSubscriber(gate, "onError", "onSuccess", "onComplete"))
    log.info("MaybeNo")
    maybeNoLetter.subscribe(MaybeDemoSubscriber(gate, "onError", "onSuccess", "onComplete"))
    log.info("Complete")
    completable.subscribe(DemoCompletableObserver(gate, "onError" , "onComplete"))
}