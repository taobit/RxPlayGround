package com.pluralsight.rxjava2.module4

import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.module2.runCode
import com.pluralsight.rxjava2.utility.MutableReference
import com.pluralsight.rxjava2.utility.datasets.FibonacciSequence
import com.pluralsight.rxjava2.utility.datasets.GreekAlphabet
import io.reactivex.rxjava3.kotlin.blockingSubscribeBy

fun main() {
    runCode {
        GreekAlphabet.greekAlphabetInGreekObservable()
                .collectInto(
                        mutableListOf<String>(),
                        { list, letter -> list.add(letter) }
                ).blockingSubscribe {
                    it.forEach(::print)
                }
    }

    runCode {
        FibonacciSequence.create(12)
                .collect(
                        { MutableReference(0L) },
                        { reference, nextVal ->
                            reference.setValue(reference.getValue() + nextVal)
                        }
                )
                .blockingSubscribeBy {
                    log.info(it.toString())
                }
    }
}
