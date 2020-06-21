package com.pluralsight.rxjava2.module4

import com.pluralsight.rxjava2.nitrite.entity.LetterPair
import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.rxjava3.kotlin.Observables
import kotlin.system.exitProcess

fun main() {
    Observables.zip(
            GreekAlphabet.greekAlphabetInGreekObservable(),
            GreekAlphabet.greekAlphabetInEnglishObservable()
    ) { greek, english -> LetterPair(greek, english) }
            .subscribe(DemoSubscriber<LetterPair>())
    exitProcess(0)
}