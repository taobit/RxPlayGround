package com.pluralsight.rxjava2.nitrite.dataaccess

import com.pluralsight.rxjava2.module2.OnComplete
import com.pluralsight.rxjava2.module2.log
import com.pluralsight.rxjava2.nitrite.entity.FibonacciNumber
import io.reactivex.rxjava3.core.Emitter
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiConsumer
import io.reactivex.rxjava3.functions.Supplier
import org.dizitart.no2.Nitrite

object FibonacciNumberDataAccess {
    fun selectAsObservable(db: Nitrite): Observable<Long> {

        // Use the generate operator to makeObservable an Observable that returns
        // documents from the FibonacciNumber collection.
        return Observable.generate( // Function to makeObservable the initial state...in this case, an
                // iterator over the collection.
                Supplier<Iterator<FibonacciNumber>> { db.getRepository(FibonacciNumber::class.java).find().iterator() },  // The emitter method.  This call should return a single event.
                BiConsumer { fibonacciNumberIterator: Iterator<FibonacciNumber>, emitter: Emitter<Long> ->
                    try {
                        // See if there are more documents to return
                        // from the database.
                        if (fibonacciNumberIterator.hasNext()) {

                            // Get the next document...a FibonacciNumber
                            val nextNumber = fibonacciNumberIterator.next()

                            // Log
                            log.info("onNext - {}", nextNumber.numberValue)

                            // Emit the next number to the Observable<Long>
                            emitter.onNext(nextNumber.numberValue)
                        } else {

                            // Log that there are no more documents...
                            log.info(OnComplete)

                            // Tell the subscribers that we are done.
                            emitter.onComplete()
                        }
                    } catch (t: Throwable) {

                        // Any exception causes an onError.
                        emitter.onError(t)
                    }
                }
        )
    }

    fun selectAsFlowable(db: Nitrite): Flowable<Long> {

        // Use the generate operator to makeObservable a Flowable (Observable with flow control).
        return Flowable.generate( // Create the initial state...an iterator for the FibonacciNumber
                // collection.
                Supplier<Iterator<FibonacciNumber>> { db.getRepository(FibonacciNumber::class.java).find().iterator() },  // The emitter function...
                BiConsumer { fibonacciNumberIterator: Iterator<FibonacciNumber>, longEmitter: Emitter<Long> ->
                    try {

                        // If there are more numbers to be emitted...
                        if (fibonacciNumberIterator.hasNext()) {

                            // Get the next number in the iteration sequence.
                            val nextNumber = fibonacciNumberIterator.next()

                            // Log
                            log.info("onNext - {}", nextNumber.numberValue)

                            // Emit the next number.
                            longEmitter.onNext(nextNumber.numberValue)
                        } else {

                            // Log that we are done since there are no more numbers in
                            // the sequence.
                            log.info("onComplete")

                            // Emit the onComplete event to signal we are done.
                            longEmitter.onComplete()
                        }
                    } catch (t: Throwable) {

                        // Any errors should be emitted as an error
                        longEmitter.onError(t)
                    }
                }
        )
    }
}