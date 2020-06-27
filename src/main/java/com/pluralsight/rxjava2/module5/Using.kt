package com.pluralsight.rxjava2.module5

import com.pluralsight.rxjava2.module2.*
import com.pluralsight.rxjava2.nitrite.NO2
import com.pluralsight.rxjava2.nitrite.NitriteTestDatabase
import com.pluralsight.rxjava2.nitrite.datasets.NitriteGreekAlphabetSchema
import com.pluralsight.rxjava2.nitrite.entity.LetterPair
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import java.io.IOException

fun main() {
    Observable.using(
            { createResource() },
            { createObservable(it) },
            { createDisposer(it) }
    ).subscribe(DemoSubscriber(gate))
    gate.waitForAny(OnComplete, OnError)
}

private fun createResource(): NitriteTestDatabase {
    try {
        log.info("init database")
        return NitriteTestDatabase(NitriteGreekAlphabetSchema())
    } catch (e: IOException) {
        logError(e)
        throw RuntimeException(e)
    }
}

private fun createObservable(database: NitriteTestDatabase): ObservableSource<LetterPair> {
    log.info("init Observable")
    return NO2.execute(database.database)
    { Observable.fromIterable(it?.getRepository(LetterPair::class.java)?.find()) }
}

private fun createDisposer(database: NitriteTestDatabase) {
    log.info("close database")
    try {
        database.close()
    } catch (e: IOException) {
        logError(e)
    }
}