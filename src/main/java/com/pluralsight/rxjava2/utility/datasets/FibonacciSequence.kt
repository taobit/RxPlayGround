package com.pluralsight.rxjava2.utility.datasets

import io.reactivex.rxjava3.core.Observable

object FibonacciSequence {
    @JvmStatic
    fun create(totalNumber: Long): Observable<Long> =
            Observable.create<Long> {
                var count = 0L
                var previousValue1 = 1L
                var previousValue2 = 1L

                while (count < totalNumber) {
                    if (it.isDisposed) {
                        break
                    }
                    count++
                    if (count == 1L) {
                        it.onNext(0L)
                        continue
                    }
                    if (count == 2L) {
                        it.onNext(1L)
                        continue
                    }
                    val newValue = previousValue1 + previousValue2
                    it.onNext(newValue)
                    previousValue1 = previousValue2
                    previousValue2 = newValue
                }
                if (!it.isDisposed) {
                    it.onComplete()
                }
            }


    @JvmStatic
    fun toArray(totalNumbers: Int) = toArrayList(totalNumbers).toTypedArray()

    @JvmStatic
    fun toArrayList(totalNumbers: Int): List<Long> =
            create(totalNumbers.toLong())
                    .collectInto(MutableList<Long>(totalNumbers) { 0 },
                            { list, num -> list.add(num) }
                    ).blockingGet()

}