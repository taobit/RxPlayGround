package com.pluralsight.rxjava2.module4

import com.pluralsight.rxjava2.nitrite.entity.LetterPair
import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.rxjava3.kotlin.zipWith

fun main() {
    GreekAlphabet.greekAlphabetInGreekObservable()
            .zipWith(
                    GreekAlphabet.greekAlphabetInEnglishObservable()
            ) { greek: String?, english: String? ->
                LetterPair(greek, english)
            }.subscribe(DemoSubscriber())
}