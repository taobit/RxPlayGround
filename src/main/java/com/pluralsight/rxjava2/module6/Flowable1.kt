package com.pluralsight.rxjava2.module6

import com.pluralsight.rxjava2.module2.OnComplete
import com.pluralsight.rxjava2.module2.OnError
import com.pluralsight.rxjava2.module2.gate
import com.pluralsight.rxjava2.module2.logError
import com.pluralsight.rxjava2.nitrite.NitriteTestDatabase
import com.pluralsight.rxjava2.nitrite.dataaccess.FibonacciNumberDataAccess
import com.pluralsight.rxjava2.nitrite.datasets.NitriteFibonacciSequenceSchema
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun main() {
    try {
        val schema = NitriteFibonacciSequenceSchema()
        NitriteTestDatabase(schema).also {
            FibonacciNumberDataAccess.selectAsObservable(it.database)
                    .observeOn(Schedulers.computation())
                    .subscribeOn(Schedulers.io())
                    .subscribe(DemoSubscriber<Long>(gate, 10, TimeUnit.MILLISECONDS))
        }
        gate.waitForAny(OnError, OnComplete)
    } catch (t: Throwable) {
        logError(t)
    }
}