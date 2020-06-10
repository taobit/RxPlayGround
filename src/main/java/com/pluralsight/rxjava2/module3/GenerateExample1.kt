package com.pluralsight.rxjava2.module3

import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.Observable
import io.reactivex.functions.BiConsumer
import java.util.concurrent.Callable


fun main() {
    val geometricSequence = makeObservable(1, 2, 8)
    geometricSequence.subscribe(DemoSubscriber())
}

fun makeObservable(start: Int, multiplier: Int, totalNumbers: Int): Observable<Int> {
    return Observable.generate(Callable { GeometricSequenceState(start, multiplier, totalNumbers) },
            BiConsumer { state, emitter ->

                // If we have reached the end, then emit and onComplete.
                if (state.count >= state.totalNumbers) {
                    emitter.onComplete()
                    return@BiConsumer
                }

                // Increment the number of values we have emitted
                state.incrementCount()

                // Emit the currently calculated
                // value of the geometric sequence.
                emitter.onNext(state.currentValue)

                // Calculate the next value in the sequence.
                state.generateNextValue()
            })
}

private data class GeometricSequenceState(var currentValue: Int, private val multiplier: Int, val totalNumbers: Int) {
    var count = 0
        private set

    fun incrementCount() = ++count


    fun generateNextValue() {
        currentValue *= multiplier
    }

}