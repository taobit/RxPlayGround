package com.pluralsight.rxjava2.module3

import com.pluralsight.rxjava2.module2.gate
import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.nitrite.NitriteTestDatabase
import com.pluralsight.rxjava2.nitrite.datasets.NitriteGreekAlphabetSchema
import com.pluralsight.rxjava2.nitrite.entity.LetterPair
import com.pluralsight.rxjava2.utility.subscribers.DemoSubscriber
import io.reactivex.rxjava3.core.Emitter
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiConsumer
import io.reactivex.rxjava3.functions.Supplier
import io.reactivex.rxjava3.schedulers.Schedulers
import org.dizitart.no2.Nitrite
import org.dizitart.no2.objects.Cursor
import java.io.IOException
import kotlin.system.exitProcess


fun main() {
    try {
        NitriteTestDatabase(NitriteGreekAlphabetSchema()).use { database ->
            val letterPairs = createLetterPairObservable(database)
            letterPairs
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.computation())
                    .subscribe(DemoSubscriber(gate))

            // Wait here...because on the other side of the try with resources the database
            // will be closed.
            gate.waitForAny("onComplete", "onError")
        }
    } catch (e: IOException) {
        log.error(e.message, e)
    }
    exitProcess(0)
}

private fun createLetterPairObservable(database: NitriteTestDatabase): Observable<LetterPair> {

    //workObservable = workObservable.doOnNext(letterPair -> log.info("doOnNext - {}", letterPair));
    return Observable.generate( // We supply a callable that will generate a starting state for this
            // operation each time 'subscribe' is called on the Observable we are returning.
            Supplier {
                NitriteCursorState(
                        database.database,  // Setup the Nitrite "Cursor" class with a simple "find" which gets
                        // all documents from the collection "LetterPair".
                        database.database
                                .getRepository(LetterPair::class.java)
                                .find()
                )
            }, BiConsumer { state: NitriteCursorState<LetterPair>, emitter: Emitter<LetterPair> ->
        try {
            // if the iterator has more LetterPairs...
            if (state.iterator.hasNext()) {
                val nextLetterPair = state.iterator.next()
                log.info("generator called - onNext {}", nextLetterPair)

                // ...then emit an onNext with the given LetterPair
                emitter.onNext(nextLetterPair)
            } else {
                log.info("generator called - onComplete")

                // No more LetterPairs...send an onComplete.
                emitter.onComplete()
            }
        } catch (t: Throwable) {
            emitter.onError(t)
        }
    })
}

data class NitriteCursorState<TCursorType>(val database: Nitrite, val cursor: Cursor<TCursorType>) {
    val iterator = cursor.iterator()
}
