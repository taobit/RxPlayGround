package com.pluralsight.rxjava2.module4

import com.pluralsight.rxjava2.module2.*
import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import com.pluralsight.rxjava2.utility.datasets.GreekLetterPair
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

fun main() {
    val threadPoolCounter1 = AtomicInteger()
    val threadPool1 = Executors.newFixedThreadPool(20) { Thread(it, "Pool 1 Thread : ${threadPoolCounter1.getAndIncrement()}") }
    val scheduler1 = Schedulers.from(threadPool1)

    val threadPoolCounter2 = AtomicInteger()
    val threadPool2 = Executors.newFixedThreadPool(20) { Thread(it, "Pool 2 Thread : ${threadPoolCounter2.getAndIncrement()}") }
    val scheduler2 = Schedulers.from(threadPool2)

    GreekAlphabet.greekAlphabetInGreekObservable()
            .flatMap({ letter ->
                val offset = GreekAlphabet.findGreekLetterOffset(letter)
                return@flatMap Observable.just(
                        letter,
                        GreekAlphabet.greekLettersInEnglish[offset],
                        GreekLetterPair(letter, GreekAlphabet.greekLettersInEnglish[offset])
                )
                        .doOnSubscribe { logOnSubscribe() }
                        .doOnNext { logNext(it) }
                        .doOnComplete { logOnComplete() }
                        .subscribeOn(scheduler2)
            }, 7)
            .observeOn(scheduler1)
            .doOnSubscribe { logOnSubscribe() }
            .subscribeBy(
                    onNext = { logNext(it) },
                    onError = { log.error(it.message) },
                    onComplete = {
                        logOnComplete()
                        gate.openGate(OnComplete)
                    }
            )
}